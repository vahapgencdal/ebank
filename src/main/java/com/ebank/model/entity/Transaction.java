package com.ebank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

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

    private String senderAccountName;
    private String senderAccountNo;
    private String senderIban;
    private double senderAccountAmount;
    private long senderCurrencyId;
    private boolean senderIsDefault;
    private long senderBankId;
    private long senderUserId;

    private String receiverAccountName;
    private String receiverAccountNo;
    private String receiverIban;
    private double receiverAccountAmount;
    private long receiverCurrencyId;
    private boolean receiverIsDefault;
    private long receiverBankId;
    private long receiverUserId;

    private double amount;
    private double fee;
    private String status;//PROCESSING,REJECTED,COMPLETED

    private LocalDateTime cDate;
    private LocalDateTime uDate;
    private long cUser;
    private long uUser;

}
