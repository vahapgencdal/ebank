package com.ebank.api;

import com.ebank.model.entity.AccountType;
import com.ebank.model.service.AccountTypeService;
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
@Path("/accountTypes")
public class AccountTypeApi {

    private final AccountTypeService accountTypeService;

    @Inject
    public AccountTypeApi(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return accountTypeService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountType> getAll() {
        return accountTypeService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountType getById(@PathParam("id") long id) {
        return accountTypeService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountType create(AccountType accountType) {
        return accountTypeService.create(accountType);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountType update(AccountType accountType) {
        return accountTypeService.update(accountType);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        accountTypeService.remove(id);
    }
}
