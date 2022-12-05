package com.example.demorest.model;

public class UserAccount extends Account {

    private String NIP;

    @Override
    public String getAccountType() {
        return "USER";
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public UserAccount(String login, String email, String password, String token, String NIP) {
        super(login, email, password, token);
        this.NIP = NIP;
    }

    @Override
    public String toString() {
        return "UserAccount{" + super.toString()
                + ", NIP='" + NIP + '\''
                + '}';
    }
}
