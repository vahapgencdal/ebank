package com.ebank.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

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
public class AccountToAccountRequest {
    private String senderAccountNo;
    private String senderBic;

    private String receiverAccountNo;
    private String receiverBic;
    private double amount;
}