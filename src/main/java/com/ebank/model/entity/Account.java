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
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Account {

    private long id;
    private String type;
    private String name;
    private String accountNo;
    private String iban;
    private double totalAmount;
    private double blockedAmount;
    private String currency;
    private boolean defaultAccount;

}
