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
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<Transaction>> getAll() {
        try {
            List<Transaction> updated = transactionService.getAll();
            return Response.of(updated, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
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
    public Response<Transaction> create(Transaction transaction) {
        try {
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
    public Response<Transaction> update(Transaction transaction) {
        try {
            Transaction updated = transactionService.update(transaction);
            return Response.of(updated, "", "OK");
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

    @GET
    @Path("complete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Boolean> completeTransaction() {
        try {
            boolean result = transactionService.completeTransaction();
            return Response.of(result, "", "OK");
        } catch (Exception e) {
            return Response.of(false, e.getMessage(), "NOK");
        }

    }

}
