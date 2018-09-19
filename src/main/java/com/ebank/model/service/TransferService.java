package com.ebank.model.service;

import com.ebank.model.entity.Transaction;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface TransferService {

    Transaction sendAccountToAccount(String senderAccountNo, String senderBic, String receiverAccountNo, String receiverBic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

    Transaction sendAccountToIban(String senderAccountNo, String senderBic, String receiverIban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

    Transaction sendIbanToAccount(String senderIban, String receiverAccountNo, String receiverBic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

    Transaction sendIbanToIban(String senderIban, String receiverIban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException;

}
