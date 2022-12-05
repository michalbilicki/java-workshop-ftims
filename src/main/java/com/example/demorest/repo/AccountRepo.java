package com.example.demorest.repo;

import com.example.demorest.exceptions.AccountNotFoundException;
import com.example.demorest.exceptions.LoginAlreadyExistsException;
import com.example.demorest.model.Account;
import com.example.demorest.model.AdminAccount;
import com.example.demorest.model.UserAccount;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class AccountRepo {

    private Map<UUID, Account> accounts;

    public AccountRepo() {
        accounts = new ConcurrentHashMap<>();
        Account account1 = new UserAccount("accountU", "some@body.U", "hashmeU", "", "1234567890");
        Account account2 = new AdminAccount("accountA", "some@body.A", "hashmeA", "", "0123456789");

        try {
            addAccount(account1);
            addAccount(account2);
        } catch (LoginAlreadyExistsException e) {
            System.out.println("I tak to jest do usuniecia");
        }
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public synchronized void addAccount(Account account) throws LoginAlreadyExistsException {
        try {
            findByLogin(account.getLogin());
            throw new LoginAlreadyExistsException();
        } catch (AccountNotFoundException anfe) {
            account.setId(UUID.randomUUID());
            accounts.put(account.getId(), account);
        }
    }

    public synchronized Account findByLogin(String login) throws AccountNotFoundException {
        return accounts.values().stream().filter(a -> a.getLogin().equals(login)).findAny().orElseThrow(AccountNotFoundException::new);
    }

    public synchronized Account findByToken(String token) throws AccountNotFoundException {
        System.out.println(token + accounts);
        return accounts.values().stream().filter(a -> a.getToken().equals(token)).findAny().orElseThrow(AccountNotFoundException::new);
    }

    public void remove(Account account) {
        accounts.remove(account.getId());
    }

    public void removeByLogin(String login) {
        try {
            remove(findByLogin(login));
        } catch (AccountNotFoundException e) {
            // It's OK! Even though it looks like exception swallowing
        }
    }
}
