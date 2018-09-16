package com.ebank.api;

import com.ebank.model.entity.Address;
import com.ebank.model.service.AddressService;
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
@Path("/addresses")
public class AddressApi {

    private final AddressService addressService;

    @Inject
    public AddressApi(AddressService addressService) {
        this.addressService = addressService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return addressService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Address> getAll() {
        return addressService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Address getById(@PathParam("id") long id) {
        return addressService.getById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Address create(Address address) {
        return addressService.create(address);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Address update(Address address) {
        return addressService.update(address);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        addressService.remove(id);
    }
}
