package com.ebank.api;

import com.ebank.model.entity.Transaction;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.request.*;
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

    @Inject
    public TransferApi(TransferService transferService) {
        this.transferService = transferService;
    }

    @POST
    @Path("internal/among")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction transferAmongItsAccounts(AmongTransferItsAccountsRequest request) throws InsufficientBalanceException, WrongBalanceTypeException {
        return transferService.transferAmongItsAccounts(request.getSenderAccountId(), request.getReceiverAccountId(), request.getAmount());
    }

    @POST
    @Path("internal/iban")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction transferAmongInternalBankAccountsWithIban(InternalTransferWithIbanRequest request) throws InsufficientBalanceException, WrongBalanceTypeException {
        return transferService.transferAmongInternalBankAccountsWithIban(request.getSenderAccountId(), request.getIban(), request.getAmount());
    }

    @POST
    @Path("internal/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction transferAmongInternalBankAccountsWithAccountNo(InternalTransferWithAccountNoTransferRequest request) throws InsufficientBalanceException, WrongBalanceTypeException {
        return transferService.transferAmongInternalBankAccountsWithAccountNo(request.getSenderAccountId(), request.getAccountNo(), request.getBic(), request.getAmount());
    }

    @POST
    @Path("external/iban")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction transferAmongExterNalBankAccountsWithIban(ExternalTransferWithIbanRequest request) throws InsufficientBalanceException, WrongBalanceTypeException {
        return transferService.transferAmongExterNalBankAccountsWithIban(request.getSenderAccountId(), request.getIban(), request.getAmount());
    }

    @POST
    @Path("external/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction transferAmongExterNalBankAccountsWithAccountNo(ExternalTransferWithAccountNoRequest request) throws InsufficientBalanceException, WrongBalanceTypeException {
        return transferService.transferAmongExterNalBankAccountsWithAccountNo(request.getSenderAccountId(), request.getAccountNo(), request.getBic(), request.getAmount());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<InternalTransferWithAccountNoTransferRequest> getAll() {
        return transferService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public InternalTransferWithAccountNoTransferRequest getById(@PathParam("id") long id) {
        return transferService.getById(id);
    }

}
