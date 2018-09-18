package com.ebank.api;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.service.BankAccountService;
import com.ebank.model.service.UserAccountService;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
@Path("/accounts")
public class AccountApi {

    private final BankAccountService bankAccountService;
    private final UserAccountService userAccountService;

    @Inject
    public AccountApi(BankAccountService bankAccountService, UserAccountService userAccountService) {
        this.bankAccountService = bankAccountService;
        this.userAccountService = userAccountService;
    }

    @GET
    @Path("/banks")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountService.getAll();
    }

    @GET
    @Path("/banks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BankAccount getBankAccountById(@PathParam("id") long id) {
        return bankAccountService.getById(id);
    }

    @POST
    @Path("/banks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BankAccount createBankAccount(BankAccount account) {
        return bankAccountService.create(account);
    }

    @PUT
    @Path("/banks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BankAccount updateBankAccount(BankAccount account) {
        return bankAccountService.update(account);
    }

    @DELETE
    @Path("/banks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void removeBankAccount(@PathParam("id") long id) {
        bankAccountService.remove(id);
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserAccount> getAllUserAccounts() {
        return userAccountService.getAll();
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccount getUserAccountById(@PathParam("id") long id) {
        return userAccountService.getById(id);
    }

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccount createUserAccount(UserAccount account) {
        return userAccountService.create(account);
    }

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccount updateUserAccount(UserAccount account) {
        return userAccountService.update(account);
    }

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void removeUserAccount(@PathParam("id") long id) {
        userAccountService.remove(id);
    }


}
