package com.ebank.model.repository.impl;

import com.ebank.executer.TransactionThreadPoolExecuterImpl;
import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.repository.BankAccountRepository;
import com.ebank.model.repository.TransactionRepository;
import com.ebank.model.repository.UserAccountRepository;
import com.ebank.util.RateConstant;
import com.ebank.util.TransactionStatus;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Slf4j
@Singleton
public class TransactionRepositoryImpl extends BaseRepositoryImpl<Transaction> implements TransactionRepository {

    private List<Transaction> transactions;



    private final BankAccountRepository bankAccountRepository;
    private final UserAccountRepository userAccountRepository;
    private static final Logger LOGGER = Logger.getLogger(TransactionThreadPoolExecuterImpl.class.getName());

    @Inject
    public TransactionRepositoryImpl(BankAccountRepository bankAccountRepository, UserAccountRepository userAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userAccountRepository = userAccountRepository;
        transactions = new ArrayList<>();
    }

    public Transaction getById(long id) {
        return this.transactions.parallelStream().filter(transaction -> transaction.getId() == id).findAny().orElse(new Transaction());
    }

    public List<Transaction> getAll() {
        return this.transactions;
    }

    @Override
    public Transaction create(Transaction transaction) {
        transaction.setId(getCurrentMaxId() + 1);
        this.transactions.add(transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        Transaction byId = this.getById(transaction.getId());
        byId.setStatus(transaction.getStatus());
        return byId;
    }

    @Override
    public void remove(long id) {
        Transaction byId = this.getById(id);
        this.transactions.remove(byId);
    }

    @Override
    public int getSize() {
        return this.transactions.size();
    }

    private long getCurrentMaxId() {
        if (this.transactions.isEmpty()) return 0;
        Ordering<Transaction> ordering = new Ordering<Transaction>() {
            @Override
            public int compare(Transaction left, Transaction right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.transactions).getId();
    }

    @Override
    public Transaction getAnyPendingTransaction() {
        if (!this.transactions.isEmpty())
            return this.transactions.parallelStream().filter(transaction -> transaction.getStatus().equals(TransactionStatus.PENDING.toString())).findAny().orElse(null);
        return null;
    }

    @Override
    public Transaction complete(Transaction transaction) {
        Transaction transactionUpdate = getById(transaction.getId());
        if (updateAccountsAmounts(transaction)) {
            transactionUpdate.setStatus(TransactionStatus.COMPLETED.toString());
        } else {
            updateRejectedAccount(transaction);
            transactionUpdate.setStatus(TransactionStatus.REJECTED.toString());
        }
        return transactionUpdate;
    }

    private void updateRejectedAccount(Transaction transaction) {
        UserAccount senderAccount = userAccountRepository.getById(transaction.getSenderAccountId());
        double senderBlockedAmount = senderAccount.getBlockedAmount() - transaction.getAmount() - transaction.getFee() * transaction.getAmount();
        senderAccount.setBlockedAmount(senderBlockedAmount);
        userAccountRepository.update(senderAccount);
    }

    private boolean updateAccountsAmounts(Transaction transaction) {
        if (transaction.getSenderBank().equals(transaction.getReceiverBank())) {

            if (transaction.getSenderUser().equals(transaction.getReceiverUser())) {
                //Transfer Money Among Same User Account
                if (transaction.getSenderCurrency().equals(transaction.getReceiverCurrency())) {
                    return sendMoneyAmongAccounts(transaction);
                } else {
                    //Transfer Money Among Same User Currencies Account:money exchange
                    return exhangeMoneyAmongAccounts(transaction);
                }
            } else {
                // Transfer Money Among In Same Bank But Different User Accounts
                if (transaction.getSenderCurrency().equals(transaction.getReceiverCurrency())) {
                    return sendMoneyAmongUserAccounts(transaction);
                } else {
                    // Transfer Money Among In Same Bank But Different User Accounts
                    return exhangeMoneyAmongAccounts(transaction);
                }
            }
        } else {
            //Transfer Money Different Bank Accounts
            if (transaction.getSenderCurrency().equals(transaction.getReceiverCurrency())) {
                return externalMoneyTransfer(transaction);
            } else {
                return exchangeExternalMoneyTransfer(transaction);
            }
        }
    }

    private boolean exchangeExternalMoneyTransfer(Transaction transaction) {
        try {
            UserAccount senderAccount = userAccountRepository.getById(transaction.getSenderAccountId());
            UserAccount receiverAccount = userAccountRepository.getById(transaction.getReceiverAccountId());

            double feeAmount = transaction.getFee() * transaction.getAmount();

            double senderAmount = senderAccount.getTotalAmount() - transaction.getAmount();
            senderAccount.setTotalAmount(senderAmount);
            double senderBlockedAmount = senderAccount.getBlockedAmount() - transaction.getAmount() - feeAmount;
            senderAccount.setBlockedAmount(senderBlockedAmount);

            BankAccount senderBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getSenderCurrency(), transaction.getSenderBank());

            BankAccount receiverSenderCurrencyBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getReceiverCurrency(), transaction.getSenderCurrency());

            BankAccount receiverCurrencyBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getReceiverCurrency(), transaction.getReceiverBank());


            double recevierBankAmount = receiverCurrencyBankAccount.getTotalAmount() - RateConstant.get(transaction.getSenderCurrency() + "_" + transaction.getReceiverCurrency()).getRate() * (transaction.getAmount() + feeAmount);
            receiverCurrencyBankAccount.setTotalAmount(recevierBankAmount);


            double receiverBankAccountAmount = receiverSenderCurrencyBankAccount.getTotalAmount() + transaction.getAmount() - feeAmount;
            receiverSenderCurrencyBankAccount.setTotalAmount(receiverBankAccountAmount);


            double senderBankAmount = senderBankAccount.getTotalAmount() - transaction.getAmount() + feeAmount;
            senderBankAccount.setTotalAmount(senderBankAmount);


            double receiverAccounAmount = receiverAccount.getTotalAmount() + RateConstant.get(transaction.getSenderCurrency() + "_" + transaction.getReceiverCurrency()).getRate() * (transaction.getAmount() - feeAmount);
            receiverAccount.setTotalAmount(receiverAccounAmount);

            userAccountRepository.update(senderAccount);
            userAccountRepository.update(receiverAccount);

            bankAccountRepository.update(senderBankAccount);
            bankAccountRepository.update(receiverSenderCurrencyBankAccount);
            bankAccountRepository.update(receiverCurrencyBankAccount);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return false;

    }

    public boolean sendMoneyAmongAccounts(Transaction transaction) {
        try {
            UserAccount senderAccount = userAccountRepository.getById(transaction.getSenderAccountId());
            UserAccount receiverAccount = userAccountRepository.getById(transaction.getReceiverAccountId());

            double senderAmount = (senderAccount.getTotalAmount() - transaction.getAmount()) - transaction.getFee() * transaction.getAmount();
            senderAccount.setTotalAmount(senderAmount);
            double senderBlockedAmount = senderAccount.getBlockedAmount() - transaction.getAmount() - transaction.getFee() * transaction.getAmount();
            senderAccount.setBlockedAmount(senderBlockedAmount);

            double receiverAmount = receiverAccount.getTotalAmount() + transaction.getAmount();
            receiverAccount.setTotalAmount(receiverAmount);

            userAccountRepository.update(senderAccount);
            userAccountRepository.update(receiverAccount);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return false;

    }

    public boolean sendMoneyAmongUserAccounts(Transaction transaction) {
        try {
            //DataSource.userAccounts
            sendMoneyAmongAccounts(transaction);
            BankAccount senderBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getSenderCurrency(), transaction.getSenderBank());

            double senderAmount = senderBankAccount.getTotalAmount() + transaction.getFee() * transaction.getAmount();
            senderBankAccount.setTotalAmount(senderAmount);

            bankAccountRepository.update(senderBankAccount);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return false;

    }


    public boolean exhangeMoneyAmongAccounts(Transaction transaction) {
        try {
            UserAccount senderAccount = userAccountRepository.getById(transaction.getSenderAccountId());
            UserAccount receiverAccount = userAccountRepository.getById(transaction.getReceiverAccountId());

            double feeAmount = transaction.getFee() * transaction.getAmount();

            double senderAmount = senderAccount.getTotalAmount() - transaction.getAmount();
            senderAccount.setTotalAmount(senderAmount);
            double senderBlockedAmount = transaction.getAmount() + feeAmount;
            senderAccount.setBlockedAmount(senderBlockedAmount);


            double receiverAccounAmount = receiverAccount.getTotalAmount() + RateConstant.get(transaction.getSenderCurrency() + "_" + transaction.getReceiverCurrency()).getRate() * (transaction.getAmount() - feeAmount);
            receiverAccount.setTotalAmount(receiverAccounAmount);

            BankAccount senderBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getSenderCurrency(), transaction.getSenderBank());

            BankAccount receiverBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getReceiverCurrency(), transaction.getReceiverBank());

            double senderBankAmount = senderBankAccount.getTotalAmount() + transaction.getAmount();
            senderBankAccount.setTotalAmount(senderBankAmount + feeAmount);

            double recevierBankAmount = receiverBankAccount.getTotalAmount() - RateConstant.get(transaction.getSenderCurrency() + "_" + transaction.getReceiverCurrency()).getRate() * (transaction.getAmount() + feeAmount);
            receiverBankAccount.setTotalAmount(recevierBankAmount);

            userAccountRepository.update(senderAccount);
            userAccountRepository.update(receiverAccount);

            bankAccountRepository.update(senderBankAccount);
            bankAccountRepository.update(receiverBankAccount);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return false;

    }

    private boolean externalMoneyTransfer(Transaction transaction) {
        try {
            UserAccount senderAccount = userAccountRepository.getById(transaction.getSenderAccountId());
            BankAccount senderBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getSenderCurrency(), transaction.getSenderBank());


            double feeAmount = transaction.getFee() * transaction.getAmount();


            double senderAmount = senderAccount.getTotalAmount() - transaction.getAmount() - feeAmount;
            senderAccount.setTotalAmount(senderAmount);

            double senderBlockedAmount = senderAccount.getBlockedAmount() - transaction.getAmount() - feeAmount;
            senderAccount.setBlockedAmount(senderBlockedAmount);

            double senderBankAccountAmount = senderBankAccount.getTotalAmount() - transaction.getAmount() + feeAmount;
            senderBankAccount.setTotalAmount(senderBankAccountAmount);

            UserAccount receiverAccount = userAccountRepository.getById(transaction.getReceiverAccountId());
            BankAccount receiverBankAccount = bankAccountRepository.getByCurrencyAndBank(transaction.getReceiverCurrency(), transaction.getReceiverBank());


            double receiverAccountAmount = receiverAccount.getTotalAmount() + transaction.getAmount();
            double receiverBankAccountAmount = receiverBankAccount.getTotalAmount() + transaction.getAmount();

            receiverAccount.setTotalAmount(receiverAccountAmount);
            receiverBankAccount.setTotalAmount(receiverBankAccountAmount);

            userAccountRepository.update(senderAccount);
            userAccountRepository.update(receiverAccount);

            bankAccountRepository.update(senderBankAccount);
            bankAccountRepository.update(receiverBankAccount);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return false;
    }


}
