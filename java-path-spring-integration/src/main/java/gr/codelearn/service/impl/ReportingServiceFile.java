package gr.codelearn.service.impl;

import gr.codelearn.domain.Account;
import gr.codelearn.domain.Directory;
import gr.codelearn.service.AccountService;
import gr.codelearn.service.ReportingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ReportingServiceFile implements ReportingService {

    private AccountService accountService;

    @Override
    public Map<String, Object> executeReports(Message message) {
        Map<String, Object> payload = (Map<String, Object>) message.getPayload();
        log.info("Executing reports.");

        logPayment(payload);
        logAccounts();
        payload.put("reportingComplete", Boolean.TRUE);
        log.info("Execution of reports finished.");
        return payload;
    }

    private void logPayment(Map<String, Object> payload) {
        log.info("Initiating the logging payment process.");
        // not so good, creates a buffer writer each time a payment is processed
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Directory.FILE_DIRECTORY.getPath() + "payments.txt"), StandardOpenOption.CREATE)) {
            String output = "Debtor " + payload.get("debtorName")
                    + " with IBAN " + payload.get("debtorIBAN")
                    + " paid creditor " + payload.get("creditorName")
                    + " with IBAN " + payload.get("creditorIBAN")
                    + " the amount of " + payload.get("paymentAmount") + "(" + payload.get("paymentCurrency") + ")."
                    + "The fee was " + payload.get("feeAmount") + "(" + payload.get("feeCurrency") + ")."
                    + " Logged at " + new Date();
            writer.write(output);
        } catch (IOException e) {
            log.error("Error writing in file directory: ({}) with exception : {}.", Directory.FILE_DIRECTORY.getPath(), e);
        }
        log.info("Logging payment has finished.");
    }

    private void logAccounts() {
        log.info("Initiating the logging accounts process.");
        // not so good, creates a buffer writer each time a payment is processed
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Directory.FILE_DIRECTORY.getPath() + "accounts.txt"), StandardOpenOption.CREATE)) {
            List<Account> accounts = accountService.findAll();
            for (Account account : accounts) {
                String output = "Account with name " + account.getName()
                        + ", IBAN " + account.getIban()
                        + " and type  " + account.getType()
                        + " has a balance of " + account.getBalance() + "."
                        + " Logged at " + new Date();
                writer.write(output);
                writer.newLine();
            }
            log.info("Logging accounts has finished successfully.");
        } catch (IOException e) {
            log.error("Error writing in file directory: ({}) with exception : {}.", Directory.FILE_DIRECTORY.getPath(), e);
        }
    }


}
