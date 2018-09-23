package com.ebank.api;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.request.AccountRequest;
import com.ebank.model.service.BankAccountService;
import com.ebank.model.service.UserAccountService;
import com.google.inject.Inject;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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
    @Path("/banks/account/{accountNo}/{bankCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> getBankAccountById(@PathParam("accountNo") String accountNo, @PathParam("bankCode") String bankCode) {
        try {
            BankAccount bankAccount = bankAccountService.getByAccountNoAndBank(accountNo, bankCode);
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    private UserAccount getUserAccount(AccountRequest req) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(req.getName());
        userAccount.setBank(req.getBank());
        userAccount.setAccountNo(req.getAccountNo());
        userAccount.setId(req.getId());
        userAccount.setType(req.getType());
        List<MonetaryAmount> amountList = new ArrayList<>();

        for (int i = 0; i < req.getAmounts().size(); i++) {
            MonetaryAmount amount1 = Monetary.getDefaultAmountFactory().setNumber(req.getAmounts().get(i).getAmount()).setCurrency(req.getAmounts().get(i).getCurrency()).create();
            amountList.add(amount1);
        }
        userAccount.setAmounts(amountList);
        userAccount.setUser(req.getUser());
        return userAccount;
    }

    private BankAccount getBankAccount(AccountRequest req) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(req.getName());
        bankAccount.setBank(req.getBank());
        bankAccount.setAccountNo(req.getAccountNo());
        bankAccount.setId(req.getId());
        bankAccount.setType(req.getType());
        List<MonetaryAmount> amountList = new ArrayList<>();
        for (int i = 0; i < req.getAmounts().size(); i++) {
            MonetaryAmount amount1 = Monetary.getDefaultAmountFactory().setNumber(req.getAmounts().get(i).getAmount()).setCurrency(req.getAmounts().get(i).getCurrency()).create();
            amountList.add(amount1);
        }
        bankAccount.setAmounts(amountList);
        return bankAccount;
    }

    @POST
    @Path("/banks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> createBankAccount(AccountRequest account) {
        try {
            BankAccount bankAccount = bankAccountService.create(getBankAccount(account));
            return Response.of(bankAccount, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @PUT
    @Path("/banks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BankAccount> updateBankAccount(AccountRequest account) {
        try {
            BankAccount bankAccount = bankAccountService.update(getBankAccount(account));
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
    @Path("/users/account/{accountNo}/{bankCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> getUserAccountByAccountNoAndBankCode(@PathParam("accountNo") String accountNo, @PathParam("bankCode") String bankCode) {
        try {
            UserAccount us = userAccountService.getByAccountNoAndBank(accountNo, bankCode);
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }


    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> createUserAccount(AccountRequest account) {
        try {
            UserAccount us = userAccountService.create(getUserAccount(account));
            return Response.of(us, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserAccount> updateUserAccount(AccountRequest account) {
        try {
            UserAccount us = userAccountService.update(getUserAccount(account));
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
