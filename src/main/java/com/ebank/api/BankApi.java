package com.ebank.api;

import com.ebank.model.entity.Bank;
import com.ebank.model.service.BankService;
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
@Path("/banks")
public class BankApi {

    private final BankService bankService;

    @Inject
    public BankApi(BankService bankService) {
        this.bankService = bankService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return bankService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bank> getAll() {
        return bankService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bank getById(@PathParam("id") long id) {
        return bankService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bank create(Bank bank) {
        return bankService.create(bank);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bank update(Bank bank) {
        return bankService.update(bank);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        bankService.remove(id);
    }
}
