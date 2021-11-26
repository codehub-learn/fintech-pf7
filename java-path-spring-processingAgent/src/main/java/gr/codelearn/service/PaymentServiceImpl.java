package gr.codelearn.service;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.domain.Account;
import gr.codelearn.domain.Payment;
import gr.codelearn.domain.exception.AccountBalanceIsNotSufficient;
import gr.codelearn.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl extends AbstractLogEntity implements PaymentService {

    private final String queueName = "payment.queue";

    private AccountRepository accountRepository;
    private ReportingService reportingService;

    @Override
    @RabbitListener(queues = queueName)
    public void processPayment(Payment payment) {
        // todo payment id is received as null, is this OK?
        // todo should this method validate if payment is OK again? hasn't this been validated in the API module?
        try {
            logger.info("Payment received: {}", payment);
            Optional<Account> debtorOptional = accountRepository.findAccountByIban(payment.getDebtor().getIban());
            if (debtorOptional.isPresent()) {
                Account debtor = debtorOptional.get();
                BigDecimal result = debtor.getBalance().subtract(payment.getPaymentAmount());
                if (result.compareTo(BigDecimal.ZERO) < 0) {
                    throw new AccountBalanceIsNotSufficient("Account " + debtor.getIban() + " has an insufficient balance for a payment.");
                }
                debtor.setBalance(result);
                accountRepository.save(debtor);
            }
            Optional<Account> creditorOptional = accountRepository.findAccountByIban(payment.getCreditor().getIban());
            if (creditorOptional.isPresent()) {
                Account creditor = creditorOptional.get();
                BigDecimal result = creditor.getBalance().add(payment.getPaymentAmount());
                creditor.setBalance(result);
                accountRepository.save(creditor);
            }
            Optional<Account> bankerOptional = accountRepository.findAccountByIban("GR00000000000001");
            if (bankerOptional.isPresent()){
                Account banker = bankerOptional.get();
                BigDecimal result = banker.getBalance().add(payment.getFeeAmount());
                banker.setBalance(result);
                accountRepository.save(banker);
            }
            reportingService.logPayment(payment);
            reportingService.logAccounts(accountRepository.findAll());
        } catch (AccountBalanceIsNotSufficient e) {
            logger.error("{}", e);
        }
    }
}
