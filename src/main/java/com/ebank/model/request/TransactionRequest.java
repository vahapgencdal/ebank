package com.ebank.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

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
public class TransactionRequest {

    private long id;

    private long senderAccountId;
    private String senderAccountName;
    private String senderAccountNo;
    private BigDecimal senderBalance;
    private String senderCurrency;
    private String senderBank;
    private String senderUser;

    private long receiverAccountId;
    private String receiverAccountName;
    private String receiverAccountNo;
    private BigDecimal receiverBalance;
    private String receiverCurrency;
    private String receiverBank;
    private String receiverUser;

    private BigDecimal amountBalance;
    private String amountCurrency;
    private BigDecimal feeAmount;
}
