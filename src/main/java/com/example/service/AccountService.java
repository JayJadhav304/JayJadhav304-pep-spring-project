package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.length() < 4) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        if (accountRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists");
        }
        Account account = new Account(username, password);
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent() && accountOptional.get().getPassword().equals(password)) {
            return accountOptional.get();
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public Optional<Account> findById(Integer accountId) {
        return accountRepository.findById(accountId);
    }

}
