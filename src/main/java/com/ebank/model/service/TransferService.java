package com.ebank.model.service;

import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.request.InternalTransferWithAccountNoRequest;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface TransferService extends BaseService<InternalTransferWithAccountNoRequest> {

    long transferAmongItsAccounts(long senderAccountId, long receiverAccountId, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

}
