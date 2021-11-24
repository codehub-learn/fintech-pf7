package gr.codelearn.service;

import gr.codelearn.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();

    Optional<Account> findByIban(String iban);

    void addAll(Iterable<Account> accounts);
}
