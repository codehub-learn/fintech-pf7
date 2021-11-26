package gr.codelearn.service;

import com.google.common.base.Strings;
import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.config.AMQPConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ProducerService extends AbstractLogEntity {

    private RabbitTemplate rabbitTemplate;

    public void produceMessage(Map<String, Object> payload){
        // replacing attributes with "" instead of null because we do are not required to check if string is null in all the following services
        //cid (string)
        if(Strings.isNullOrEmpty((String) payload.get("cid"))) {
            payload.put("cid", "");
        }
        //creditorName (string)
        if(Strings.isNullOrEmpty((String) payload.get("creditorName"))) {
            payload.put("creditorName", "");
        }
        //creditorAccount (string)
        if(Strings.isNullOrEmpty((String) payload.get("creditorAccount"))) {
            payload.put("creditorAccount", "");
        }
        //debtorName (string)
        if(Strings.isNullOrEmpty((String) payload.get("debtorName"))) {
            payload.put("debtorName", "");
        }
        //debtorAccount (string)
        if(Strings.isNullOrEmpty((String) payload.get("debtorAccount"))) {
            payload.put("debtorAccount", "");
        }
        //paymentAmount (string)
        if(Strings.isNullOrEmpty((String) payload.get("paymentAmount"))) {
            payload.put("paymentAmount", "");
        }
        //valueDate (string)
        if(Strings.isNullOrEmpty((String) payload.get("valueDate"))) {
            payload.put("valueDate", "");
        }
        //paymentCurrency (string)
        if(Strings.isNullOrEmpty((String) payload.get("paymentCurrency"))) {
            payload.put("paymentCurrency", "");
        }
        //feeAmount (string)
        if(Strings.isNullOrEmpty((String) payload.get("feeAmount"))) {
            payload.put("feeAmount", "");
        }
        //feeCurrency (string)
        if(Strings.isNullOrEmpty((String) payload.get("feeCurrency"))) {
            payload.put("feeCurrency", "");
        }
        payload.forEach((k, v) -> logger.info("key: {}, value: {} ", k, v));
        rabbitTemplate.convertAndSend(AMQPConfiguration.exchangeName, AMQPConfiguration.routingKey, payload);
        logger.info("A payload has been sent to the queue.");
    }
}
