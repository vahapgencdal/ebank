package com.ebank.api;

import com.ebank.model.entity.Account;
import com.ebank.model.service.AccountService;
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

    private final AccountService accountService;

    @Inject
    public AccountApi(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return accountService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getById(@PathParam("id") long id) {
        return accountService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account create(Account account) {
        return accountService.create(account);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Account update(Account account) {
        return accountService.update(account);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        accountService.remove(id);
    }
}
