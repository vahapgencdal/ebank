package com.ebank.api;

import com.ebank.model.entity.Transaction;
import com.ebank.model.request.TransactionRequest;
import com.ebank.model.service.TransactionService;
import com.google.inject.Inject;

import javax.money.Monetary;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
@Path("/transactions")
public class TransactionApi {

    private final TransactionService transactionService;

    @Inject
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<Transaction>> getAll() {
        try {
            List<Transaction> updated = transactionService.getAll();
            return Response.of(updated, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    private Transaction getTransaction(TransactionRequest tr) {
        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(tr.getSenderAccountId());
        transaction.setSenderAccountNo(tr.getSenderAccountNo());
        transaction.setSenderBalance(Monetary.getDefaultAmountFactory().setCurrency(tr.getSenderCurrency()).setNumber(tr.getSenderBalance()).create());
        transaction.setSenderAccountName(tr.getSenderAccountName());
        transaction.setReceiverAccountId(tr.getReceiverAccountId());
        transaction.setReceiverBalance(Monetary.getDefaultAmountFactory().setCurrency(tr.getReceiverCurrency()).setNumber(tr.getReceiverBalance()).create());
        transaction.setReceiverAccountNo(tr.getReceiverAccountNo());
        transaction.setReceiverAccountName(tr.getReceiverAccountName());
        transaction.setAmount(Monetary.getDefaultAmountFactory().setCurrency(tr.getAmountCurrency()).setNumber(tr.getAmountBalance()).create());
        transaction.setFeeAmount(Monetary.getDefaultAmountFactory().setCurrency(tr.getReceiverCurrency()).setNumber(tr.getFeeAmount()).create());
        transaction.setSenderUser(tr.getSenderUser());
        transaction.setReceiverUser(tr.getReceiverUser());
        transaction.setSenderBank(tr.getSenderBank());
        transaction.setReceiverBank(tr.getReceiverBank());

        return transaction;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> getById(@PathParam("id") long id) {
        try {
            Transaction updated = transactionService.getById(id);
            return Response.of(updated, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> create(TransactionRequest req) {
        try {
            Transaction transaction = getTransaction(req);

            Transaction updated = transactionService.create(transaction);
            return Response.of(updated, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> update(TransactionRequest req) {
        try {
            Transaction transaction = getTransaction(req);
            return Response.of(transaction, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }

    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Object> remove(@PathParam("id") long id) {
        try {
            transactionService.remove(id);
            return Response.of(null, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

}
