package gr.codelearn.controller;

import gr.codelearn.domain.Account;
import gr.codelearn.service.AccountService;
import gr.codelearn.service.ProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api")
public class PaymentController {

    private AccountService accountService;
    //private PaymentService paymentService;
    private ProducerService producerService;

//    @PostMapping("pay")
//    public String pay(@RequestBody @Valid PaymentDTO paymentDTO) {
//        try {
//            // todo shouldn't this be done in an object mapper or something?
//            Account creditor = Account.builder().name(paymentDTO.getCreditorName()).iban(paymentDTO.getCreditorAccount()).build();
//            Account debtor = Account.builder().name(paymentDTO.getDebtorName()).iban(paymentDTO.getDebtorAccount()).build();
//            // todo already validated through hibernate validator
//            Date valueDate = DateParser.convertFromString(paymentDTO.getValueDate());
//            Payment payment = Payment.builder().creditor(creditor).debtor(debtor).valueDate(valueDate).paymentCurrency(paymentDTO.getPaymentCurrency()).paymentAmount(paymentDTO.getPaymentAmount()).feeCurrency(paymentDTO.getFeeCurrency()).feeAmount(paymentDTO.getFeeAmount()).build();
//            paymentService.pay(payment);
//            return "Payment queued!";
//        } catch (InvalidAccountException | ParseException e) {
//            return "Error occurred: " + e.getMessage();
//        }
//    }

    @PostMapping("feeder")
    public boolean feederEndpoint(@RequestBody Map<String, Object> payload) {
        producerService.produceMessage(payload);
        return true;
    }

    @GetMapping("accounts")
    public List<Account> findAllAccounts() {
        // for testing purposes
        return accountService.findAll();
    }


}
