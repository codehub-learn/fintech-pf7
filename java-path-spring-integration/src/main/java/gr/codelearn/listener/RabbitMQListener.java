package gr.codelearn.listener;

import gr.codelearn.gateway.PaymentsGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQListener {

    private static final String queueName = "payment.queue";

    private PaymentsGateway paymentsGateway;

    @RabbitListener(queues = queueName)
    public void consumePayment(Map<String, Object> payload) {
        log.info("A payment payload has been received.");
        paymentsGateway.initiatePayment(payload);
    }
}
