package com.ebank.api;

import com.ebank.model.entity.Currency;
import com.ebank.model.service.CurrencyService;
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
@Path("/currencies")
public class CurrencyApi {

    private final CurrencyService currencyService;

    @Inject
    public CurrencyApi(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return currencyService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Currency> getAll() {
        return currencyService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Currency getById(@PathParam("id") long id) {
        return currencyService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Currency create(Currency currency) {
        return currencyService.create(currency);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Currency update(Currency currency) {
        return currencyService.update(currency);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        currencyService.remove(id);
    }
}
