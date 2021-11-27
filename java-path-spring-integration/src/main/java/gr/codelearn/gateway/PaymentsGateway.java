package gr.codelearn.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

@MessagingGateway
public interface PaymentsGateway {
    @Gateway(requestChannel = "accountsLookupChannel")
    void initiatePayment(Map<String, Object> payload);
}
