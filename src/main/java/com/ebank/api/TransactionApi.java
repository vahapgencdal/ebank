package com.ebank.api;

import com.ebank.model.entity.Transaction;
import com.ebank.model.service.TransactionService;
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
@Path("/transactions")
public class TransactionApi {

    private final TransactionService transactionService;

    @Inject
    public TransactionApi(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return transactionService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction getById(@PathParam("id") long id) {
        return transactionService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction create(Transaction transaction) {
        return transactionService.create(transaction);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction update(Transaction transaction) {
        return transactionService.update(transaction);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        transactionService.remove(id);
    }
}
