package com.ebank.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 18.09.2018
 * @description TODO: Class Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class TransferRequest {
    private String senderAccountNo;
    private String senderBankCode;
    private String senderCurrencyCode;

    private String receiverAccountNo;
    private String receiverBankCode;
    private String receiverCurrencyCode;
    private BigDecimal amount;
}
