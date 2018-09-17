package com.ebank.api;

import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.request.AmongTransferItsAccountsRequest;
import com.ebank.model.request.InternalTransferWithAccountNoRequest;
import com.ebank.model.service.TransferService;
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
@Path("/transfers")
public class TransferApi {

    private final TransferService transferService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public long transferAmongItsAccounts(AmongTransferItsAccountsRequest request) throws InsufficientBalanceException, WrongBalanceTypeException {
        return transferService.transferAmongItsAccounts(request.getSenderAccountId(), request.getReceiverAccountId(), request.getAmount());
    }


    @Inject
    public TransferApi(TransferService transferService) {
        this.transferService = transferService;
    }

    @GET
    @Path("size")
    @Produces(MediaType.APPLICATION_JSON)
    public int getSize() {
        return transferService.getSize();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<InternalTransferWithAccountNoRequest> getAll() {
        return transferService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public InternalTransferWithAccountNoRequest getById(@PathParam("id") long id) {
        return transferService.getById(id);
    }



    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public InternalTransferWithAccountNoRequest update(InternalTransferWithAccountNoRequest internalTransferWithAccountNo) {
        return transferService.update(internalTransferWithAccountNo);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") long id) {
        transferService.remove(id);
    }
}
