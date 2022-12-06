package com.example.demorest.manager;

import com.example.demorest.exceptions.AccountNotFoundException;
import com.example.demorest.exceptions.LoginAlreadyExistsException;
import com.example.demorest.model.*;
import com.example.demorest.repo.AccountRepo;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/accounts")
public class AccountResource {

    @Inject
    private AccountRepo accountRepo;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/log_in")
    public Account logIn(@Valid Credentials credentials) {
        try {
            Account existingAccount = checkPassword(credentials);
            String token = "Token-" + existingAccount.getLogin();
            existingAccount.setToken(token);
            Thread.sleep(2000);
            return existingAccount;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/log_out")
    public Account logOut(@HeaderParam("Authorization") String authorization) {
        try {
            Account existingAccount = getAuthenticatedAccount(authorization);
            existingAccount.setToken("");
            Thread.sleep(2000);
            return existingAccount;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAllAccounts(@HeaderParam("Authorization") String authorization) {
        try {
            getAdminAccount(authorization);
            Thread.sleep(2000);
            return accountRepo.getAllAccounts();
        } catch (ForbiddenException ex) {
            throw ex;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/existing_account")
    @Produces(MediaType.APPLICATION_JSON)
    public Account activeAccount(
            @HeaderParam("Authorization") String authorization
    ) {
        try {
            Thread.sleep(2000);
            return getAuthenticatedAccount(authorization);
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account addAccount(
            @Valid Account account,
            @HeaderParam("Authorization") String authorization
    ) {
        try {
            getAdminAccount(authorization);
            accountRepo.addAccount(account);
            Thread.sleep(2000);
            return account;
        } catch (ForbiddenException ex) {
            throw ex;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        } catch (LoginAlreadyExistsException e) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
    }

    @PUT
    @Path("/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account updateAccount(
            Account account,
            @PathParam("login") String login,
            @HeaderParam("Authorization") String authorization
    ) {
        try {
            getAdminAccount(authorization);
            Account existingAccount = accountRepo.findByLogin(login);
            existingAccount.setEmail(account.getEmail());
            Thread.sleep(2000);
            return existingAccount;
        } catch (ForbiddenException ex) {
            throw ex;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{login}")
    public Account findByLogin(
            @PathParam("login") String login,
            @HeaderParam("Authorization") String authorization
    ) {
        try {
            Account authenticatedAccount = getAuthenticatedAccount(authorization);
            if (authenticatedAccount.getLogin().equals(login)) {
                return authenticatedAccount;
            }

            getAdminAccount(authorization);
            Thread.sleep(2000);
            return accountRepo.findByLogin(login);
        } catch (ForbiddenException ex) {
            throw ex;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/{login}")
    public void removeAccount(
            @PathParam("login") String login,
            @HeaderParam("Authorization") String authorization
    ) {
        try {
            getAdminAccount(authorization);
            accountRepo.removeByLogin(login);
            Thread.sleep(2000);
        } catch (ForbiddenException ex) {
            throw ex;
        } catch (AccountNotFoundException | InterruptedException ex) {
            throw new NotFoundException();
        }
    }

    private Account checkPassword(Credentials credentials) throws AccountNotFoundException {
        Account existingAccount = accountRepo.findByLogin(credentials.getLogin());
        if (!existingAccount.getPassword().equals(credentials.getPassword())) {
            throw new AccountNotFoundException();
        }
        return existingAccount;
    }

    private Account getAdminAccount(String authorization) throws AccountNotFoundException {
        Account currentAccount = accountRepo.findByToken(authorization);
        if (!currentAccount.getAccountType().equals("ADMIN")) {
            throw new ForbiddenException();
        }
        return currentAccount;
    }

    private Account getAuthenticatedAccount(String authorization) throws AccountNotFoundException {
        return accountRepo.findByToken(authorization);
    }
}
