package com.ebank.api;

import com.ebank.model.entity.CounterParty;
import com.ebank.model.service.CounterPartyService;
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
@Path("/counterParties")
public class CounterPartyApi {

    private final CounterPartyService counterPartyService;

    @Inject
    public CounterPartyApi(CounterPartyService counterPartyService) {
        this.counterPartyService = counterPartyService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return counterPartyService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CounterParty> getAll() {
        return counterPartyService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CounterParty getById(@PathParam("id") long id) {
        return counterPartyService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CounterParty create(CounterParty counterParty) {
        return counterPartyService.create(counterParty);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CounterParty update(CounterParty counterParty) {
        return counterPartyService.update(counterParty);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        counterPartyService.remove(id);
    }
}
