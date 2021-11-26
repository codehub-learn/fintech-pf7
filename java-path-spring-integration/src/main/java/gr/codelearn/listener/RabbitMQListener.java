package gr.codelearn.listener;

import gr.codelearn.service.BalanceInquiryService;
import gr.codelearn.service.BeneficiaryService;
import gr.codelearn.service.PostingService;
import gr.codelearn.service.ReportingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.stream.Collectors.joining;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQListener {

    private static final String queueName = "payment.queue";

    private BeneficiaryService beneficiaryService;
    private BalanceInquiryService balanceInquiryService;
    private PostingService postingService;
    private ReportingService reportingService;

    @RabbitListener(queues = queueName)
    public void consumePayment(Map<String, Object> payload) {
        String concatenatedKeysAndValues = payload.entrySet().stream().map(entry ->
                        "key: '" + entry.getKey() + "' value: '" + entry.getValue() + "' |")
                .collect(joining(" "));
        log.info("Received payload from queue with the following contents: {}.", concatenatedKeysAndValues);

        beneficiaryService.validate(payload);
        balanceInquiryService.checkTransactionFinancially(payload);
        postingService.makeTransaction(payload);
        reportingService.executeReports(payload);
        log.info("Processing the payload has finished.");
    }
}
