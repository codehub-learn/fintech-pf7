package gr.codelearn.service;

import gr.codelearn.domain.Account;
import gr.codelearn.domain.Directory;
import gr.codelearn.domain.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

@Service
@Slf4j
public class ReportingServiceFile implements ReportingService {

    @Override
    public void logPayment(Payment payment) {
        // todo, not so good, creates a buffer writer each time a payment is processed
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Directory.FILE_DIRECTORY.getPath() + "payments.txt"), StandardOpenOption.CREATE)) {
                String output = "Debtor " + payment.getDebtor().getName()
                        + " with IBAN " + payment.getDebtor().getIban()
                        + " paid creditor " + payment.getCreditor().getName()
                        + " with IBAN " + payment.getCreditor().getIban()
                        + " the amount of " + payment.getPaymentAmount() + "(" + payment.getPaymentCurrency() + ")."
                        + "The fee was " + payment.getFeeAmount() + "(" + payment.getFeeCurrency() + ")."
                        + " Logged at " + new Date();
                writer.write(output);
        } catch (IOException e) {
            log.error("Error writing in file directory: ({}) with exception : {}", Directory.FILE_DIRECTORY.getPath(), e);
        }
    }

    @Override
    public void logAccounts(Iterable<Account> accounts) {
        // todo, not so good, creates a buffer writer each time a payment is processed
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Directory.FILE_DIRECTORY.getPath() + "accounts.txt"), StandardOpenOption.CREATE)) {
            for (Account account : accounts) {
                String output = "Account with name " + account.getName()
                        + ", IBAN " + account.getIban()
                        + " and type  " + account.getType()
                        + " has a balance of " + account.getBalance() + "."
                        + " Logged at " + new Date();
                writer.write(output);
                writer.newLine();
            }

        } catch (IOException e) {
            log.error("Error writing in file directory: ({}) with exception : {}", Directory.FILE_DIRECTORY.getPath(), e);
        }
    }


}
