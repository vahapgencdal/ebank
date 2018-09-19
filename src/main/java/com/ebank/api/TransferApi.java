package com.ebank.api;

import com.ebank.model.entity.Transaction;
import com.ebank.model.request.newfolder.AccountToAccountRequest;
import com.ebank.model.request.newfolder.AccountToIbanRequest;
import com.ebank.model.request.newfolder.IbanToAccountRequest;
import com.ebank.model.request.newfolder.IbanToIbanRequest;
import com.ebank.model.service.TransferService;
import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
@Path("/transfers")
public class TransferApi {

    private final TransferService transferService;

    @Inject
    public TransferApi(TransferService transferService) {
        this.transferService = transferService;
    }


    @POST
    @Path("accountToAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> sendAccountToAccount(AccountToAccountRequest request) {
        try {
            Transaction tr = transferService.sendAccountToAccount(request.getSenderAccountNo(), request.getSenderBic(), request.getReceiverAccountNo(), request.getReceiverBic(), request.getAmount());
            return Response.of(tr, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @POST
    @Path("accountToIban")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> sendAccountToIban(AccountToIbanRequest request) {
        try {
            Transaction tr = transferService.sendAccountToIban(request.getSenderAccountNo(), request.getSenderBic(), request.getReceiverIban(), request.getAmount());
            return Response.of(tr, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

    @POST
    @Path("ibanToIban")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> sendIbanToIban(IbanToIbanRequest request) {
        try {
            Transaction tr = transferService.sendIbanToIban(request.getSenderIban(), request.getReceiverIban(), request.getAmount());
            return Response.of(tr, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }
    @POST
    @Path("ibanToAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Transaction> sendIbanToAccount(IbanToAccountRequest request) {
        try {
            Transaction tr = transferService.sendIbanToAccount(request.getSenderIban(), request.getReceiverAccountNo(), request.getReceiverBic(), request.getAmount());
            return Response.of(tr, "", "OK");
        } catch (Exception e) {
            return Response.of(null, e.getMessage(), "NOK");
        }
    }

}
