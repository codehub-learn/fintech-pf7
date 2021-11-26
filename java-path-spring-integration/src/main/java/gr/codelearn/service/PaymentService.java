package gr.codelearn.service;


import gr.codelearn.domain.Payment;

public interface PaymentService {
    void processPayment(Payment payment);
}
