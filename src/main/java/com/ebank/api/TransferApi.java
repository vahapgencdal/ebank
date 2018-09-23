package com.ebank.api;

import com.ebank.model.request.TransferRequest;
import com.ebank.model.service.TransferService;
import com.google.inject.Inject;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<?> transfer(TransferRequest request) {
        try {
            MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setNumber(request.getAmount()).setCurrency(request.getSenderCurrencyCode()).create();
            transferService.transfer(request.getSenderAccountNo(), request.getSenderBankCode(), request.getReceiverAccountNo(), request.getReceiverBankCode(), request.getSenderCurrencyCode(), request.getReceiverCurrencyCode(), monetaryAmount);
            return Response.of(true, "", "OK");
        } catch (Exception e) {
            return Response.of(false, e.getMessage(), "NOK");
        }
    }

}
