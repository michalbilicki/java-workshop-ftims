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
        try {
            addAccount(new UserAccount("BasiaUser", "Basia", "User", "basia.user@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("MichalUser", "Michal", "User", "michal.user@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("TestUser", "Test", "User", "test.user@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("OlaUser", "Ola", "User", "ola.user@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("SzymonUser", "Szymon", "User", "szymon.user@gmail.com", "P@ssw0rd!", "", "1234567890"));

            addAccount(new UserAccount("BasiaUser2", "Basia", "User", "basia.user2@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("MichalUser2", "Michal", "User", "michal.user2@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("TestUser2", "Test", "User", "test.user2@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("OlaUser2", "Ola", "User", "ola.user2@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("SzymonUser2", "Szymon", "User", "szymon.user2@gmail.com", "P@ssw0rd!", "", "1234567890"));

            addAccount(new UserAccount("BasiaUser3", "Basia", "User", "basia.user3@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("MichalUser3", "Michal", "User", "michal.user3@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("TestUser3", "Test", "User", "test.user3@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("OlaUser3", "Ola", "User", "ola.user3@gmail.com", "P@ssw0rd!", "", "1234567890"));
            addAccount(new UserAccount("SzymonUser3", "Szymon", "User", "szymon.user3@gmail.com", "P@ssw0rd!", "", "1234567890"));

            addAccount(new AdminAccount("BasiaAdmin", "Basia", "Admin", "basia.admin@admin.com", "P@ssw0rd!", "", "0123456789"));
            addAccount(new AdminAccount("MichalAdmin", "Michal", "Admin", "michal.admin@admin.com", "P@ssw0rd!", "", "0123456789"));
            addAccount(new AdminAccount("TestAdmin", "Test", "Admin", "test.admin@admin.com", "P@ssw0rd!", "", "0123456789"));
            addAccount(new AdminAccount("OlaAdmin", "Ola", "Admin", "ola.admin@admin.com", "P@ssw0rd!", "", "0123456789"));
            addAccount(new AdminAccount("SzymonAdmin", "Szymon", "Admin", "szymon.admin@admin.com", "P@ssw0rd!", "", "0123456789"));

            addAccount(new AdminAccount("BarbaraBorkowska", "Barbara", "Borkowska", "barbara.borkowska@htdevelopers.com", "P@ssw0rd!", "", "0123456789"));
            addAccount(new AdminAccount("MichalBilicki", "Michal", "Bilicki", "michal.bilicki@htdevelopers.com", "P@ssw0rd!", "", "0123456789"));
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
