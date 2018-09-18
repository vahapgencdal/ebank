package com.ebank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String senderIban;
    private double senderAccountAmount;
    private String senderCurrency;
    private boolean senderDefaultAccount;
    private String senderBank;
    private String senderUser;

    private long receiverAccountId;
    private String receiverAccountName;
    private String receiverAccountNo;
    private String receiverIban;
    private double receiverAccountAmount;
    private String receiverCurrency;
    private boolean receiverDefaultAccount;
    private String receiverBank;
    private String receiverUser;

    private double amount;
    private double fee;
    private String status;//PROCESSING,REJECTED,COMPLETED


    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", senderAccountId=" + senderAccountId + ", senderAccountName='" + senderAccountName + '\'' + ", senderAccountNo='" + senderAccountNo + '\'' + ", senderIban='" + senderIban + '\'' + ", senderAccountAmount=" + senderAccountAmount + ", senderCurrency='" + senderCurrency + '\'' + ", senderDefaultAccount=" + senderDefaultAccount + ", senderBank='" + senderBank + '\'' + ", senderUser='" + senderUser + '\'' + ", receiverAccountId=" + receiverAccountId + ", receiverAccountName='" + receiverAccountName + '\'' + ", receiverAccountNo='" + receiverAccountNo + '\'' + ", receiverIban='" + receiverIban + '\'' + ", receiverAccountAmount=" + receiverAccountAmount + ", receiverCurrency='" + receiverCurrency + '\'' + ", receiverDefaultAccount=" + receiverDefaultAccount + ", receiverBank='" + receiverBank + '\'' + ", receiverUser='" + receiverUser + '\'' + ", amount=" + amount + ", fee=" + fee + ", status='" + status + '\'' + '}';
    }
}
