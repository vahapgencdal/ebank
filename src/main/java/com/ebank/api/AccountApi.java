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
    public Response<List<BankAccount>> getAllBankAccounts() {
        try {
            List<BankAccount> bankAccount = bankAccountService.getAll();
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @GET
    @Path("/banks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> getBankAccountById(@PathParam("id") long id) {
        try {
            BankAccount bankAccount = bankAccountService.getById(id);
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }


    @GET
    @Path("/banks/account/{no}/{bic}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> getBankAccountById(@PathParam("no") String no, @PathParam("bic") String bic) {
        try {
            BankAccount bankAccount = bankAccountService.getByAccountNo(no, bic);
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @POST
    @Path("/banks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> createBankAccount(BankAccount account) {
        try {
            BankAccount bankAccount = bankAccountService.create(account);
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @PUT
    @Path("/banks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> updateBankAccount(BankAccount account) {
        try {
            BankAccount bankAccount = bankAccountService.update(account);
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @DELETE
    @Path("/banks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Object> removeBankAccount(@PathParam("id") long id) {
        try {
            bankAccountService.remove(id);
            return Response.of(null, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserAccount>> getAllUserAccounts() {
        try {
            List<UserAccount> us = userAccountService.getAll();
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> getUserAccountById(@PathParam("id") long id) {
        try {
            UserAccount us = userAccountService.getById(id);
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @GET
    @Path("/users/account/{no}/{bic}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> getUserAccountById(@PathParam("no") String no, @PathParam("bic") String bic) {
        try {
            UserAccount us = userAccountService.getByAccountNo(no, bic);
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }


    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> createUserAccount(UserAccount account) {
        try {
            UserAccount us = userAccountService.create(account);
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> updateUserAccount(UserAccount account) {
        try {
            UserAccount us = userAccountService.update(account);
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Object> removeUserAccount(@PathParam("id") long id) {
        try {
            userAccountService.remove(id);
            return Response.of(null, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }

    }


}
