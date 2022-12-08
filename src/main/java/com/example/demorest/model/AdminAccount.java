package com.example.demorest.model;

public class AdminAccount extends Account {

    private String phone;

    @Override
    public String getAccountType() {
        return "ADMIN";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AdminAccount(String login, String firstName, String lastName, String email, String password, String token, String phone) {
        super(login, firstName, lastName, email, password, token);
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "AdminAccount{" + super.toString()
                + ", NIP='" + phone + '\''
                + '}';
    }
}
