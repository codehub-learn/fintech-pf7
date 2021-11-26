package gr.codelearn.service;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.config.AMQPConfiguration;
import gr.codelearn.domain.Account;
import gr.codelearn.domain.Payment;
import gr.codelearn.domain.exception.InvalidAccountException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl extends AbstractLogEntity implements PaymentService {

    RabbitTemplate rabbitTemplate;
    AccountService accountService;

    @Override
    public void pay(Payment payment) throws InvalidAccountException {
        try {
            boolean validated = validate(payment);
            if (validated) {
                rabbitTemplate.convertAndSend(AMQPConfiguration.exchangeName, AMQPConfiguration.routingKey, payment);
                logger.info("A payment has been queued.");
            }
        } catch (InvalidAccountException e) {
            // I do not want the caller of this method to know exactly what the error in the back-end was
            throw new InvalidAccountException("Account(s) details are wrong.");
        }
    }

    private boolean validate(Payment payment) throws InvalidAccountException {
        Optional<Account> creditorOptional = accountService.findByIban(payment.getCreditor().getIban());
        if (creditorOptional.isPresent()) {
            // todo should we also validate based on name?
            if (!creditorOptional.get().getName().equals(payment.getCreditor().getName())) {
                throw new InvalidAccountException("Creditor name does not match.");
            }
            // set attributes such as creditor database ID etc.
            payment.setCreditor(creditorOptional.get());
        } else {
            throw new InvalidAccountException("Creditor does not exist.");
        }

        Optional<Account> debtorOptional = accountService.findByIban(payment.getDebtor().getIban());
        if (debtorOptional.isPresent()) {
            // todo should we also validate based on name?
            if (!debtorOptional.get().getName().equals(payment.getDebtor().getName())) {
                throw new InvalidAccountException("Debtor name does not match.");
            }
            // set attributes such as creditor database ID etc.
            payment.setDebtor(debtorOptional.get());
        } else {
            throw new InvalidAccountException("Debtor does not exist.");
        }
        return true;
    }
}
