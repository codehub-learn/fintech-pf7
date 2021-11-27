package gr.codelearn.service;

import com.google.common.base.Strings;
import gr.codelearn.config.AMQPConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class FeederService {

    private RabbitTemplate rabbitTemplate;

    public void feederRequest(Map<String, Object> payload) {
        rabbitTemplate.convertAndSend(AMQPConfiguration.exchangeName, AMQPConfiguration.routingKey, validate(payload));
        log.info("A payload has been sent to the queue.");
    }

    private Map<String, Object> validate(Map<String, Object> payload) {
        // replacing attributes with "" instead of null because we do are not required to check if string is null in all the following services
        //cid (string)
        if (Strings.isNullOrEmpty((String) payload.get("cid"))) {
            payload.put("cid", "");
        }
        //creditorName (string)
        if (Strings.isNullOrEmpty((String) payload.get("creditorName"))) {
            payload.put("creditorName", "");
        }
        //creditorAccount (string)
        if (Strings.isNullOrEmpty((String) payload.get("creditorIBAN"))) {
            payload.put("creditorIBAN", "");
        }
        //debtorName (string)
        if (Strings.isNullOrEmpty((String) payload.get("debtorName"))) {
            payload.put("debtorName", "");
        }
        //debtorAccount (string)
        if (Strings.isNullOrEmpty((String) payload.get("debtorIBAN"))) {
            payload.put("debtorIBAN", "");
        }
        //paymentAmount (string)
        if (Strings.isNullOrEmpty((String) payload.get("paymentAmount"))) {
            payload.put("paymentAmount", 0);
        }
        //valueDate (string)
        if (Strings.isNullOrEmpty((String) payload.get("valueDate"))) {
            payload.put("valueDate", new Date());
        }
        //paymentCurrency (string)
        if(Strings.isNullOrEmpty((String) payload.get("paymentCurrency"))) {
            payload.put("paymentCurrency", "");
        }
        //feeAmount (string)
        if(Strings.isNullOrEmpty((String) payload.get("feeAmount"))) {
            payload.put("feeAmount", 0);
        }
        //feeCurrency (string)
        if(Strings.isNullOrEmpty((String) payload.get("feeCurrency"))) {
            payload.put("feeCurrency", "");
        }
        return payload;
    }
}
