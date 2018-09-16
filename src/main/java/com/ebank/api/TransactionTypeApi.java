package com.ebank.api;

import com.ebank.model.entity.TransactionType;
import com.ebank.model.service.TransactionTypeService;
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
@Path("/transactionTypes")
public class TransactionTypeApi {

    private final TransactionTypeService transactionTypeService;

    @Inject
    public TransactionTypeApi(TransactionTypeService transactionTypeService) {
        this.transactionTypeService = transactionTypeService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return transactionTypeService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TransactionType> getAll() {
        return transactionTypeService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionType getById(@PathParam("id") long id) {
        return transactionTypeService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionType create(TransactionType transactionType) {
        return transactionTypeService.create(transactionType);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionType update(TransactionType transactionType) {
        return transactionTypeService.update(transactionType);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        transactionTypeService.remove(id);
    }
}
