package gr.codelearn.service;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.domain.Payment;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl extends AbstractLogEntity implements PaymentService {

    private final String queueName = "payment.queue";

    @Override
    @RabbitListener(queues = queueName)
    public void processPayment(Payment payment) {
        // todo payment id is received as null, is this OK?
        // todo should this method validate if payment is OK again> hasn't this been validated in the API module?
        logger.info("Payment received: {}", payment);
    }
}
