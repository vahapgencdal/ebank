package com.ebank.model.service.impl;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.exception.InSufficientBalanceException;
import com.ebank.model.exception.TargetNotFoundException;
import com.ebank.model.exception.WrongAmountException;
import com.ebank.model.service.BankAccountService;
import com.ebank.model.service.TransactionService;
import com.ebank.model.service.TransferService;
import com.ebank.model.service.UserAccountService;
import com.ebank.util.CurrencyEnum;
import com.ebank.util.StaticUtil;
import com.ebank.util.TransactionCreater;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransferServiceImpl implements TransferService {

    private final UserAccountService userAccountService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @Inject
    public TransferServiceImpl(UserAccountService userAccountService, TransactionService transactionService, BankAccountService bankAccountService) {
        this.userAccountService = userAccountService;
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
    }


    @Override
    public synchronized void transfer(String senderAccountNo, String senderBankCode, String receiverAccountNo, String receiverBankCode, String senderCurrency, String receiverCurrency, MonetaryAmount amount) throws InSufficientBalanceException, WrongAmountException, TargetNotFoundException {

        if (amount.compareTo(Monetary.getDefaultAmountFactory().setCurrency(amount.getCurrency()).setNumber(0).create()) <= 0) {
            throw new WrongAmountException("Amount must be bigger than zero");
        }

        if (senderAccountNo.equals(receiverAccountNo) && senderBankCode.equals(receiverBankCode)) {
            throw new IllegalArgumentException("Redundant Transfer");
        }

        UserAccount senderAccount = userAccountService.getByAccountNoAndBank(senderAccountNo, senderBankCode);
        UserAccount receiverAccount = userAccountService.getByAccountNoAndBank(receiverAccountNo, receiverBankCode);

        if (senderAccount == null)
            throw new TargetNotFoundException("Account Not Found with " + senderAccountNo + " And " + senderBankCode);
        if (receiverAccount == null)
            throw new TargetNotFoundException("Account Not Found with " + receiverAccountNo + " And " + receiverBankCode);

        BankAccount senderBank = bankAccountService.getByBankCode(senderBankCode);
        BankAccount receiverBank = bankAccountService.getByBankCode(receiverBankCode);
        if (senderBank == null)
            throw new TargetNotFoundException("Bank Account Not Found with " + senderCurrency + " And " + senderAccount.getBank());
        if (receiverBank == null)
            throw new TargetNotFoundException("Bank Account Not Found with " + receiverCurrency + " And " + receiverAccount.getBank());

        if (senderCurrency.equals(receiverCurrency)) {
            MonetaryAmount feeAmount = StaticUtil.getTotalFeeAmount(senderBankCode, receiverBankCode, amount);

            MonetaryAmount withdrawAmount = amount.add(feeAmount);

            senderAccount.withdraw(withdrawAmount);
            receiverAccount.deposit(amount);

            userAccountService.update(senderAccount);
            userAccountService.update(receiverAccount);

            Transaction userMoneyTransfer = TransactionCreater.createUserTransaction(senderAccount, receiverAccount, amount, feeAmount);
            transactionService.create(userMoneyTransfer);

            if (!senderAccount.getUser().equals(receiverAccount.getUser()) || !senderAccount.getBank().equals(receiverAccount.getBank())) {
                senderBank.deposit(feeAmount);
                if (!senderBank.getBank().equals(receiverBank.getBank())) {
                    senderBank.withdraw(amount);
                    receiverBank.deposit(amount);
                    bankAccountService.update(receiverBank);
                }
                bankAccountService.update(senderBank);
                Transaction bankMoneyTransfer = TransactionCreater.createBankTransaction(senderBank, receiverBank, amount, feeAmount);
                transactionService.create(bankMoneyTransfer);
            }
            System.out.println(amount + "| " + feeAmount + "| " + senderAccount.getMonetary(senderCurrency) + "| " + receiverAccount.getMonetary(receiverCurrency) + "| " + senderBank.getMonetary(senderCurrency) + "| " + receiverBank.getMonetary(receiverCurrency));
        } else {
            double bankFee = StaticUtil.getBankFee(senderBankCode, receiverBankCode);
            MonetaryAmount bankFeeAmount = StaticUtil.getFeeAmount(bankFee, amount);

            double currencyRate = StaticUtil.getCurrencyExhangeRate(senderCurrency, receiverCurrency);
            MonetaryAmount exchangeAmount = StaticUtil.getExhangeAmount(currencyRate, receiverCurrency, amount);

            MonetaryAmount withdrawAmount = amount.add(bankFeeAmount);

            senderAccount.withdraw(withdrawAmount);
            senderBank.withdraw(amount);
            senderBank.deposit(bankFeeAmount);

            receiverBank.deposit(exchangeAmount);
            receiverAccount.deposit(exchangeAmount);
            System.out.println(amount + "| " + bankFeeAmount + "| " + senderAccount.getMonetary(senderCurrency) + "| " + receiverAccount.getMonetary(receiverCurrency) + "| " + senderBank.getMonetary(senderCurrency) + "| " + receiverBank.getMonetary(receiverCurrency));
        }

    }

    private static List<MonetaryAmount> getMonetaryList(BigDecimal balance) {
        List<MonetaryAmount> amountList = new ArrayList<>();
        amountList.add(Monetary.getDefaultAmountFactory().setNumber(balance).setCurrency(Monetary.getCurrency(CurrencyEnum.EUR.name())).create());
        amountList.add(Monetary.getDefaultAmountFactory().setNumber(balance).setCurrency(Monetary.getCurrency(CurrencyEnum.USD.name())).create());
        return amountList;
    }
/*
    public static void main(String[] args) {

        UserAccount vhpAccount = new UserAccount();
        vhpAccount.setAmounts(getMonetaryList(new BigDecimal(1000)));
        vhpAccount.setUser(UserEnum.VAHAP.getVal());
        vhpAccount.setBank(BankEnum.IS.getVal());
        vhpAccount.setType(AccountTypeEnum.DRAW.toString());
        vhpAccount.setAccountNo(UUID.randomUUID().toString());
        vhpAccount.setName("Vahap Account");

        UserAccount emreAccount = new UserAccount();
        emreAccount.setAmounts(getMonetaryList(new BigDecimal(1000)));
        emreAccount.setUser(UserEnum.EMRE.getVal());
        emreAccount.setBank(BankEnum.GARAN.getVal());
        emreAccount.setType(AccountTypeEnum.DRAW.toString());
        emreAccount.setAccountNo(UUID.randomUUID().toString());
        emreAccount.setName("Emre Account");

        UserAccountService userAccountService = new UserAccountServiceImpl(new UserAccountRepositoryImpl());
        TransactionService transactionService = new TransactionServiceImpl(new TransactionRepositoryImpl());
        BankAccountService bankAccountService = new BankAccountServiceImpl(new BankAccountRepositoryImpl());


        userAccountService.create(vhpAccount);
        userAccountService.create(emreAccount);

        BankAccount bankGaranAccount = new BankAccount();
        bankGaranAccount.setName("Garanti Own Account");
        bankGaranAccount.setAmounts(getMonetaryList(new BigDecimal(1000)));
        bankGaranAccount.setAccountNo(UUID.randomUUID().toString());
        bankGaranAccount.setType(AccountTypeEnum.DRAW.toString());
        bankGaranAccount.setBank(BankEnum.GARAN.getVal());

        BankAccount bankIsAccount = new BankAccount();
        bankIsAccount.setName("IsBank Own Account");
        bankIsAccount.setAmounts(getMonetaryList(new BigDecimal(1000)));
        bankIsAccount.setAccountNo(UUID.randomUUID().toString());
        bankIsAccount.setType(AccountTypeEnum.DRAW.toString());
        bankIsAccount.setBank(BankEnum.IS.getVal());

        bankAccountService.create(bankGaranAccount);
        bankAccountService.create(bankIsAccount);

        TransferService transferService = new TransferServiceImpl(userAccountService, transactionService, bankAccountService);
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            l.add(i);
        }

        l.parallelStream().forEach(integer -> {
            try {
                Random rand = new Random();
                if (integer % 2 == 0) {
                    MonetaryAmount ma = Monetary.getDefaultAmountFactory().setCurrency(Monetary.getCurrency(CurrencyEnum.EUR.name())).setNumber(new BigDecimal(integer)).create();
                    transferService.transfer(vhpAccount.getAccountNo(), vhpAccount.getBank(), emreAccount.getAccountNo(), emreAccount.getBank(), CurrencyEnum.EUR.name(), CurrencyEnum.USD.name(), ma);
                } else {
                    MonetaryAmount ma = Monetary.getDefaultAmountFactory().setCurrency(Monetary.getCurrency(CurrencyEnum.USD.name())).setNumber(new BigDecimal(integer)).create();
                    transferService.transfer(emreAccount.getAccountNo(), emreAccount.getBank(), vhpAccount.getAccountNo(), vhpAccount.getBank(), CurrencyEnum.USD.name(), CurrencyEnum.EUR.name(), ma);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        List<Transaction> transactions = transactionService.getAll();

        //  transactions.stream().forEach(transaction -> System.out.println(transaction.toString()));
    }
*/

}
