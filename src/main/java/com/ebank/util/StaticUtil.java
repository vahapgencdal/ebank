package com.ebank.util;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class StaticUtil {
    public static double getBankFee(String senderBank, String receiverBank) {
        if (senderBank.equals(receiverBank)) {
            return FeeConstant.get(FeeConstant.INTERNAL.toString()).getRate();
        } else {
            return FeeConstant.get(FeeConstant.EXTERNAL.toString()).getRate();
        }
    }

    public static double getCurrencyExhangeRate(String senderCurrency, String receiverCurrency) {
        if (senderCurrency.equals(receiverCurrency)) {
            return 0;
        } else {
            return RateConstant.get(senderCurrency + "_" + receiverCurrency).getRate();
        }
    }

    public static MonetaryAmount getFeeAmount(double fee, MonetaryAmount amount) {
        return Monetary.getDefaultAmountFactory().setCurrency(amount.getCurrency()).setNumber(new BigDecimal(0)).create().add(amount).multiply(fee);
    }

    public static MonetaryAmount getExhangeAmount(double rate, String exchangeCurrency, MonetaryAmount amount) {
        return Monetary.getDefaultAmountFactory().setCurrency(exchangeCurrency).setNumber(amount.getNumber()).create().multiply(rate);
    }

    public static MonetaryAmount getTotalFeeAmount(String senderBank, String receiverBank, MonetaryAmount amount) {

        double bankFee = getBankFee(senderBank, receiverBank);
        return getFeeAmount(bankFee, amount);

    }

}
