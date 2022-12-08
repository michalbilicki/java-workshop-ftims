package com.example.demorest.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class Account {

    private UUID id;
    @NotNull
    private String login;
    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private String token;

    public String getAccountType() {
        return "INVALID";
    }

    public Account(String login, String firstName, String lastName, String email, String password, String token) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return "Account{"
                + "id=" + id
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", token='" + token + '\''
                + '}';
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
