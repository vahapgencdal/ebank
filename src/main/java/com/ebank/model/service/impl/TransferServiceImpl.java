package com.ebank.model.service.impl;

import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.service.TransactionService;
import com.ebank.model.service.TransferService;
import com.ebank.model.service.UserAccountService;
import com.ebank.util.FeeConstant;
import com.ebank.util.TransactionStatus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransferServiceImpl implements TransferService {

    private final UserAccountService userAccountService;
    private final TransactionService transactionService;

    @Inject
    public TransferServiceImpl(UserAccountService userAccountService, TransactionService transactionService) {
        this.userAccountService = userAccountService;
        this.transactionService = transactionService;
    }

    private double getFee(UserAccount senderAccount, UserAccount receiverAccount) {
        if (senderAccount.getBank().equals(receiverAccount.getBank())) {
            if (senderAccount.getUser().equals(receiverAccount.getUser())) {
                if (senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
                    return 0;
                } else {
                    return FeeConstant.get(senderAccount.getCurrency() + "_" + receiverAccount.getCurrency()).getRate();
                }

            } else {
                return FeeConstant.get(FeeConstant.INTERNAL.name()).getRate();
            }
        } else {
            return FeeConstant.get(FeeConstant.EXTERNAL.name()).getRate();
        }
    }


    private Transaction createTransaction(UserAccount senderAccount, UserAccount receiverAccount, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {

        double feeRate = getFee(senderAccount, receiverAccount);
        double senderBlockedAmount = senderAccount.getBlockedAmount() + amount + feeRate * amount;

        if (senderAccount.getTotalAmount() - senderBlockedAmount < amount + feeRate * amount) {
            throw new InsufficientBalanceException("Your Account Amount little than you want to send and fee");
        } else if (amount < 0 || amount == 0) {
            throw new WrongBalanceTypeException("Amount must be bigger than zero");
        } else {

            senderAccount.setBlockedAmount(senderBlockedAmount);

            Transaction transaction = new Transaction();
            transaction.setSenderAccountId(senderAccount.getId());
            transaction.setSenderIban(senderAccount.getIban());
            transaction.setSenderAccountNo(senderAccount.getAccountNo());
            transaction.setSenderAccountAmount(senderAccount.getTotalAmount());
            transaction.setSenderAccountName(senderAccount.getName());
            transaction.setSenderBank(senderAccount.getBank());
            transaction.setSenderCurrency(senderAccount.getCurrency());
            transaction.setSenderDefaultAccount(senderAccount.isDefaultAccount());
            transaction.setSenderUser(senderAccount.getUser());

            transaction.setReceiverAccountId(receiverAccount.getId());
            transaction.setReceiverIban(receiverAccount.getIban());
            transaction.setReceiverAccountNo(receiverAccount.getAccountNo());
            transaction.setReceiverAccountAmount(receiverAccount.getTotalAmount());
            transaction.setReceiverAccountName(receiverAccount.getName());
            transaction.setReceiverBank(receiverAccount.getBank());
            transaction.setReceiverCurrency(receiverAccount.getCurrency());
            transaction.setReceiverDefaultAccount(receiverAccount.isDefaultAccount());
            transaction.setReceiverUser(receiverAccount.getUser());

            transaction.setStatus(TransactionStatus.PENDING.toString());
            transaction.setAmount(amount);
            transaction.setFee(feeRate);
            Transaction t = transactionService.create(transaction);
            userAccountService.update(senderAccount);
            return t;
        }

    }

    @Override
    public Transaction sendAccountToAccount(String senderAccountNo, String senderBic, String receiverAccountNo, String receiverBic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderUserAccount = userAccountService.getByAccountNo(senderAccountNo, senderBic);
        UserAccount receiverUserAccount = userAccountService.getByAccountNo(receiverAccountNo, receiverBic);

        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }

    @Override
    public Transaction sendAccountToIban(String senderAccountNo, String senderBic, String receiverIban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderUserAccount = userAccountService.getByAccountNo(senderAccountNo, senderBic);
        UserAccount receiverUserAccount = userAccountService.getByIban(receiverIban);

        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }

    @Override
    public Transaction sendIbanToAccount(String senderIban, String receiverAccountNo, String receiverBic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderUserAccount = userAccountService.getByIban(senderIban);
        UserAccount receiverUserAccount = userAccountService.getByAccountNo(receiverAccountNo, receiverBic);

        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }

    @Override
    public Transaction sendIbanToIban(String senderIban, String receiverIban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderUserAccount = userAccountService.getByIban(senderIban);
        UserAccount receiverUserAccount = userAccountService.getByIban(receiverIban);

        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }
}
