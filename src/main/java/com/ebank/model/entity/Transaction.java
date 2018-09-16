package com.ebank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
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
    private String transactionType;
    private String senderAccountNo;
    private String senderIban;
    private String senderFullName;
    private String receiverAccountNo;
    private String receiverIban;
    private String receiverFullName;
    private BigDecimal amount;
    private BigDecimal fee;//
    private String status;//PROCESSING,REJECTED,COMPLETED

    private LocalDateTime cDate;
    private LocalDateTime cUSer;

}
