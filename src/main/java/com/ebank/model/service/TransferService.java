package com.ebank.model.service;

import com.ebank.model.exception.InSufficientBalanceException;
import com.ebank.model.exception.TargetNotFoundException;
import com.ebank.model.exception.WrongAmountException;

import javax.money.MonetaryAmount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface TransferService {

    void transfer(String senderAccountNo, String senderBankCode, String receiverAccountNo, String receiverBankCode, String senderCurrency, String receiverCurrency, MonetaryAmount amount) throws InSufficientBalanceException, WrongAmountException, TargetNotFoundException;
}
