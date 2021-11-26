package gr.codelearn.service;

import gr.codelearn.domain.Account;
import gr.codelearn.domain.Payment;

public interface ReportingService {
    void logPayment(Payment payment);
    void logAccounts(Iterable<Account> accounts);
}
