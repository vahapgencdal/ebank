package com.ebank.model.service;

import com.ebank.model.entity.Transaction;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.request.InternalTransferWithAccountNoTransferRequest;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface TransferService extends BaseService<InternalTransferWithAccountNoTransferRequest> {

    Transaction transferAmongItsAccounts(long senderAccountId, long receiverAccountId, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

    Transaction transferAmongInternalBankAccountsWithIban(long senderAccountId, String iban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

    Transaction transferAmongInternalBankAccountsWithAccountNo(long senderAccountId, String accountNo, String bic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

    Transaction transferAmongExterNalBankAccountsWithIban(long senderAccountId, String iban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;


    Transaction transferAmongExterNalBankAccountsWithAccountNo(long senderAccountId, String accountNo, String bic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

}
