package com.ebank.api;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.service.BankAccountService;
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

    @Inject
    public AccountApi(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return bankAccountService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BankAccount> getAll() {
        return bankAccountService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BankAccount getById(@PathParam("id") long id) {
        return bankAccountService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BankAccount create(BankAccount account) {
        return bankAccountService.create(account);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BankAccount update(BankAccount account) {
        return bankAccountService.update(account);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        bankAccountService.remove(id);
    }
}
