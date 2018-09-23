package com.ebank.model.entity;

import com.ebank.model.exception.InSufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import javax.money.UnknownCurrencyException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Account {

    private long id;
    private String type;
    private String name;
    private String accountNo;
    private List<MonetaryAmount> amounts;


    public MonetaryAmount getMonetary(String currency) {
        return amounts.parallelStream().filter(monetaryAmount -> monetaryAmount.getCurrency().getCurrencyCode().equals(currency)).findAny().get();
    }

    public synchronized void deposit(MonetaryAmount amount) throws IllegalArgumentException, UnknownCurrencyException {
        MonetaryAmount moa = getMonetary(amount.getCurrency().getCurrencyCode());
        if (moa.getCurrency() != null) {
            for (int i = 0; i < this.amounts.size(); i++) {
                if (this.amounts.get(i).getCurrency().getCurrencyCode().equals(amount.getCurrency().getCurrencyCode())) {
                    this.amounts.set(i, this.amounts.get(i).add(amount));
                }
            }
        } else {
            throw new UnknownCurrencyException(amount.getCurrency().getCurrencyCode());
        }

    }

    public synchronized void withdraw(MonetaryAmount amount) throws IllegalArgumentException, InSufficientBalanceException {

        MonetaryAmount moa = getMonetary(amount.getCurrency().getCurrencyCode());
        if (moa.getCurrency() != null) {
            if (moa.compareTo(amount) < 0) {
                throw new InSufficientBalanceException("The Balance You want to withdraw bigger than your balance");
            } else {
                for (int i = 0; i < this.amounts.size(); i++) {
                    if (this.amounts.get(i).getCurrency().getCurrencyCode().equals(amount.getCurrency().getCurrencyCode())) {
                        this.amounts.set(i, this.amounts.get(i).subtract(amount));
                    }
                }
            }
        } else {
            throw new UnknownCurrencyException(amount.getCurrency().getCurrencyCode());
        }
    }

/*
    public static void main(String[] args) {
        MonetaryAmount tlAmount = Monetary.getDefaultAmountFactory().setNumber(240.50).setCurrency(Monetary.getCurrency(CurrencyEnum.EUR.name())).create();
        MonetaryAmount usdAmount = Monetary.getDefaultAmountFactory().setNumber(360.50).setCurrency(Monetary.getCurrency(CurrencyEnum.USD.name())).create();
        List<MonetaryAmount> amountList = new ArrayList<>();
        amountList.add(tlAmount);
        amountList.add(usdAmount);
        Account account = new Account();
        account.setAmounts(amountList);


        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            l.add(i);
        }

        l.parallelStream().forEach(integer -> {
            try {
                Random rand = new Random();
                MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(Monetary.getCurrency(CurrencyEnum.EUR.name())).setNumber(rand.nextInt(100 + 1 + 10)).create();
                if (integer % 2 == 0) {
                    account.withdraw(monetaryAmount);
                } else {
                    account.deposit(monetaryAmount);
                }
                System.out.println(account.getMonetary(CurrencyEnum.EUR.name()) + ":" + monetaryAmount.getNumber());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });


    }
*/
}
