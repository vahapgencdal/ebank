package com.ebank.mock;

import com.ebank.model.request.AccountRequest;
import com.ebank.model.request.Amount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class AccountMockCreater {

    private static List<Amount> getAmountList(BigDecimal balance) {
        List<Amount> amountList = new ArrayList<>();
        amountList.add(new Amount(balance, "USD"));
        amountList.add(new Amount(balance, "EUR"));

        return amountList;
    }

    public static AccountRequest getIsBankOwnAccount(String accountType, String bankId, BigDecimal balance) {


        AccountRequest account = new AccountRequest();

        account.setName("Is Bank Account");
        account.setAmounts(getAmountList(balance));
        account.setAccountNo(UUID.randomUUID().toString());
        account.setType(accountType);
        account.setBank(bankId);
        return account;
    }

    public static AccountRequest getGarantiOwnAccount(String accountType, String bankId, BigDecimal balance) {

        AccountRequest account = new AccountRequest();
        account.setName("Garanti Account");
        account.setAmounts(getAmountList(balance));
        account.setAccountNo(UUID.randomUUID().toString());
        account.setType(accountType);
        account.setBank(bankId);
        return account;
    }


    public static AccountRequest getUserAccount(String userId, String accountType, String name, String bankId, BigDecimal balance) {

        AccountRequest account = new AccountRequest();
        account.setName(name);
        account.setUser(userId);
        account.setAmounts(getAmountList(balance));
        account.setAccountNo(UUID.randomUUID().toString());
        account.setType(accountType);
        account.setBank(bankId);
        return account;
    }

}
