package gr.codelearn.service.impl;

import gr.codelearn.domain.Account;
import gr.codelearn.service.AccountLookupService;
import gr.codelearn.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AccountLookupServiceImpl implements AccountLookupService {

    private AccountService accountService;

    public Map<String, Object> validate(Map<String, Object> payload) {
        //Map<String, Object> payload = (Map<String, Object>) message.getPayload();
        log.info("Performing validation.");
        // validate creditor
        String creditorIBAN = (String) payload.get("creditorIBAN");
        Optional<Account> creditorOptional = accountService.findByIban(creditorIBAN);
        if (creditorOptional.isPresent()) {
            Account creditor = creditorOptional.get();
            if (!creditor.getName().equals(payload.get("creditorName"))) {
                log.info("Stopping validation, creditor name from feeder is not valid.");
                payload.put("checkBeneficiaries", Boolean.FALSE);
                return payload;
            }
        } else {
            log.info("Stopping validation, creditor does not exist.");
            payload.put("checkBeneficiaries", Boolean.FALSE);
            return payload;
        }

        // validate debtor
        String debtorIBAN = (String) payload.get("debtorIBAN");
        Optional<Account> debtorOptional = accountService.findByIban(debtorIBAN);
        if (debtorOptional.isPresent()) {
            Account debtor = debtorOptional.get();
            if (!debtor.getName().equals(payload.get("debtorName"))) {
                log.info("Stopping validation, debtor name from feeder is not valid.");
                payload.put("checkBeneficiaries", Boolean.FALSE);
                return payload;
            }
        } else {
            log.info("Stopping validation, debtor does not exist.");
            payload.put("checkBeneficiaries", Boolean.FALSE);
            return payload;
        }

        log.info("Performing validation has finished successfully.");
        payload.put("checkBeneficiaries", Boolean.TRUE);
        return payload;
    }
}
