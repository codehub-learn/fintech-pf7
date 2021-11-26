package gr.codelearn.bootstrap;

import gr.codelearn.base.AbstractLogEntity;
import gr.codelearn.domain.Account;
import gr.codelearn.domain.AccountType;
import gr.codelearn.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class DatabaseInitializer extends AbstractLogEntity implements CommandLineRunner {

    private AccountService accountService;

    @Override
    public void run(String... args) {
        logger.info("Attempting to initialize application data.");
        if (accountService.findAll().isEmpty()) {
            logger.info("Initialization of data is needed.");
            accountService.addAll(
                    List.of(
                            Account.builder().name("Thomas Thomaidis").iban("GR44025635700006").type(AccountType.NORMAL).balance(new BigDecimal("1700")).build(),
                            Account.builder().name("Dimitris Iraklis").iban("GR74813235701234").type(AccountType.NORMAL).balance(new BigDecimal("1100")).build(),
                            Account.builder().name("Ioannis Danis").iban("GR42122635750096").type(AccountType.NORMAL).balance(new BigDecimal("25000")).build(),
                            Account.builder().name("National Bank").iban("GR67225635712356").type(AccountType.BANKER).balance(new BigDecimal("0")).build()
                    )
            );
            logger.info("Initialization of data has ended.");
        } else {
            logger.info("Initialization of data is not needed.");
        }
    }

}
