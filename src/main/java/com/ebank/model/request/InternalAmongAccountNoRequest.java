package com.ebank.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 19.09.2018
 * @description TODO: Class Description
 */
@XmlRootElement
@Data
public class InternalAmongAccountNoRequest {
    private String senderAccountNo;
    private String receiverAccountNo;
    private String senderBic;
    private String receiverBic;
    private double amount;
}
