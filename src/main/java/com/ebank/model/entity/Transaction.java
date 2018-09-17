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
    private long transactionType;
    private String senderAccountNo;
    private String senderIban;
    private String senderFullName;
    private String senderBic;
    private String receiverAccountNo;
    private String receiverIban;
    private String receiverFullName;
    private String receiverBic;
    private double amount;
    private double fee;
    private String status;//PROCESSING,REJECTED,COMPLETED

    private LocalDateTime cDate;
    private LocalDateTime cUSer;

}
