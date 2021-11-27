package gr.codelearn.flow;

import com.google.common.base.Strings;
import gr.codelearn.service.AccountLookupService;
import gr.codelearn.service.BalanceInquiryService;
import gr.codelearn.service.PostingService;
import gr.codelearn.service.ReportingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

@Configuration
@EnableIntegration
@Slf4j
@AllArgsConstructor
public class PaymentsFlow {

    private AccountLookupService accountLookupService;
    private BalanceInquiryService balanceInquiryService;
    private PostingService postingService;
    private ReportingService reportingService;

    @Bean
    public MessageChannel accountsLookupChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel balanceInquiryChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel postingChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel errorChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow accountLookupFlow(MessageChannel balanceInquiryChannel, MessageChannel errorChannel) {
        return IntegrationFlows
                .from(accountsLookupChannel())
                .log() // logs message with logger
                .transform(accountLookupService::validate)
                .log() // logging after transformation
                .route((Function<Map<String, Object>, Boolean>) this::checkBeneficiaries, routerSpec -> routerSpec
                        .subFlowMapping(true, successFlow -> successFlow.gateway(balanceInquiryChannel))
                        .subFlowMapping(false, errorFlow -> errorFlow.gateway(errorChannel)))
                .get();
    }

    @Bean
    public IntegrationFlow balanceInquiryFlow(MessageChannel postingChannel, MessageChannel errorChannel) {
        return IntegrationFlows
                .from(balanceInquiryChannel())
                .transform(balanceInquiryService::checkTransactionFinancially)
                .log() // logging after transformation
                .route((Function<Map<String, Object>, Boolean>) this::checkTransactionFinancially, routerSpec -> routerSpec
                        .subFlowMapping(true, successFlow -> successFlow.gateway(postingChannel))
                        .subFlowMapping(false, errorFlow -> errorFlow.gateway(errorChannel)))
                .get();
    }

    @Bean
    public IntegrationFlow postingFlow() {
        return IntegrationFlows
                .from(postingChannel())
                .transform(postingService::makeTransaction)
                .log() // logging after transformation
                .handle(message -> reportingService.executeReports(message))
                .get();
    }

    @Bean
    public IntegrationFlow errorFlow() {
        return IntegrationFlows
                .from(errorChannel())
                .handle(message -> log.error("Cancel payment: {}", message.getPayload()))
                .get();
    }

    public boolean checkBeneficiaries(Map<String, Object> payload) {
        return Boolean.parseBoolean(payload.get("checkBeneficiaries").toString());
    }

    public boolean checkTransactionFinancially(Map<String, Object> payload) {
        if (!Strings.isNullOrEmpty(payload.get("referralID").toString())) {
            try {
                Integer.parseInt(payload.get("referralID").toString());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private Map<String, Object> transformer(Map<String, Object> payload) {
        SimpleDateFormat existingFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        SimpleDateFormat wantedFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String formattedValueDate = payload.get("valueDate").toString();
        try {
            Date valueDate = existingFormat.parse(payload.get("valueDate").toString());
            formattedValueDate = wantedFormat.format(valueDate);
        } catch (ParseException e) {
            log.error("Parsing exception: {}", e.getMessage());
        }
        payload.put("valueDate", formattedValueDate);
        return payload;
    }

}
