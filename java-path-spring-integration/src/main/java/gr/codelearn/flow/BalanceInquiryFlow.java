package gr.codelearn.flow;

import com.google.common.base.Strings;
import gr.codelearn.service.BalanceInquiryService;
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
public class BalanceInquiryFlow {

    private BalanceInquiryService balanceInquiryService;

    @Bean
    public MessageChannel balanceInquiryChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow balanceInquiryInternalFlow(MessageChannel postingChannel, MessageChannel errorChannel) {
        return IntegrationFlows
                .from(balanceInquiryChannel())
                .transform(balanceInquiryService::checkTransactionFinancially)
                .<Map<String, Object>, Boolean>route(this::checkTransactionFinancially, message -> message
                        .channelMapping(true, postingChannel)
                        .channelMapping(false, errorChannel)
                )
                .get();
    }

    public boolean checkTransactionFinancially(Map<String, Object> payload) {
        if (!Strings.isNullOrEmpty((String) payload.get("referralID"))) {
            try {
                Integer.parseInt((String) payload.get("referralID"));
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
