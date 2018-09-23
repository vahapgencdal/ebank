package com.ebank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Transaction {

    private long id;

    private long senderAccountId;
    private String senderAccountName;
    private String senderAccountNo;
    private MonetaryAmount senderBalance;
    private String senderBank;
    private String senderUser;

    private long receiverAccountId;
    private String receiverAccountName;
    private String receiverAccountNo;
    private MonetaryAmount receiverBalance;
    private String receiverBank;
    private String receiverUser;

    private MonetaryAmount amount;
    private MonetaryAmount feeAmount;

    @Override
    public String toString() {
        return "{" + "id:" + id + ", senderAccountId:" + senderAccountId + ", senderAccountName:'" + senderAccountName + '\'' + ", senderAccountNo:'" + senderAccountNo + '\'' + ", senderBalance:" + senderBalance + ", senderBank:'" + senderBank + '\'' + ", senderUser:'" + senderUser + '\'' + ", receiverAccountId:" + receiverAccountId + ", receiverAccountName:'" + receiverAccountName + '\'' + ", receiverAccountNo:'" + receiverAccountNo + '\'' + ", receiverBalance:" + receiverBalance + ", receiverBank:'" + receiverBank + '\'' + ", receiverUser:'" + receiverUser + '\'' + ", amount:" + amount + ", feeAmount:" + feeAmount + '}';
    }
}
