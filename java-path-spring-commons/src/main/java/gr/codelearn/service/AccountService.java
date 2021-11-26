package gr.codelearn.service;

import gr.codelearn.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();

    Optional<Account> findByIban(String iban);

    void saveAll(Iterable<Account> accounts);

    void save(Account account);
}
