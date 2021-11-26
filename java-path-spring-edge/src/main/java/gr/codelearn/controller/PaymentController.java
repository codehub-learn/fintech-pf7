package gr.codelearn.controller;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.domain.Account;
import gr.codelearn.domain.Payment;
import gr.codelearn.domain.exception.InvalidAccountException;
import gr.codelearn.service.AccountService;
import gr.codelearn.service.PaymentService;
import gr.codelearn.service.ProducerService;
import gr.codelearn.transfer.PaymentDTO;
import gr.codelearn.util.DateParser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class PaymentController extends AbstractLogEntity {

    private AccountService accountService;
    private PaymentService paymentService;
    private ProducerService producerService;

    @PostMapping("pay")
    public String pay(@RequestBody @Valid PaymentDTO paymentDTO) {
        try {
            // todo shouldn't this be done in an object mapper or something?
            Account creditor = Account.builder().name(paymentDTO.getCreditorName()).iban(paymentDTO.getCreditorAccount()).build();
            Account debtor = Account.builder().name(paymentDTO.getDebtorName()).iban(paymentDTO.getDebtorAccount()).build();
            // todo already validated through hibernate validator
            Date valueDate = DateParser.convertFromString(paymentDTO.getValueDate());
            Payment payment = Payment.builder().creditor(creditor).debtor(debtor).valueDate(valueDate).paymentCurrency(paymentDTO.getPaymentCurrency()).paymentAmount(paymentDTO.getPaymentAmount()).feeCurrency(paymentDTO.getFeeCurrency()).feeAmount(paymentDTO.getFeeAmount()).build();
            paymentService.pay(payment);
            return "Payment queued!";
        } catch (InvalidAccountException | ParseException e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("accounts")
    public List<Account> findAllAccounts() {
        return accountService.findAll();
    }

    @PostMapping("feeder")
    public boolean feederEndpoint(@RequestBody Map<String, Object> payload) {
        producerService.produceMessage(payload);
        return true;
    }
}
