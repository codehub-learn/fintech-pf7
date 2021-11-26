package gr.codelearn.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQListener {

    private final String queueName = "payment.queue";

    @RabbitListener(queues = queueName)
    public void processPayment(Message message) {
        log.info("hello");
    }
}
