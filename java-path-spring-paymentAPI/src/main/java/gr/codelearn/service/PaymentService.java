package gr.codelearn.service;

import gr.codelearn.domain.Payment;
import gr.codelearn.domain.exception.InvalidAccountException;

public interface PaymentService {
    void pay(Payment payment) throws InvalidAccountException;
}
