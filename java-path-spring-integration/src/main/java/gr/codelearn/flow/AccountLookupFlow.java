package gr.codelearn.flow;

import gr.codelearn.service.AccountLookupService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.Map;

@Configuration
@EnableIntegration
@AllArgsConstructor
public class AccountLookupFlow {

    private AccountLookupService accountLookupService;

    @Bean
    public MessageChannel accountsLookupChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow accountLookupInternalFlow(MessageChannel balanceInquiryChannel, MessageChannel errorChannel) {
        return IntegrationFlows
                .from(accountsLookupChannel())
                .transform(accountLookupService::validate)
                .<Map<String, Object>, Boolean>route(this::checkBeneficiaries, message -> message
                        .channelMapping(true, balanceInquiryChannel)
                        .channelMapping(false, errorChannel)
                )
                .get();
    }

    public Boolean checkBeneficiaries(Map<String, Object> payload) {
        return (Boolean) payload.get("checkBeneficiaries");
    }
}
