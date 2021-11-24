package gr.codelearn.service;

import gr.codelearn.domain.Account;
import gr.codelearn.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findByIban(String iban) {
        return accountRepository.findAccountByIban(iban);
    }

    @Override
    public void addAll(Iterable<Account> accounts) {
        accountRepository.saveAll(accounts);
    }
}
