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
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Account {

    private long id;
    private long accountType;//DEPOSIT DRAW
    private String name;
    private String accountNo;
    private String iban;
    private double totalAmount;
    private double blockedAmount;
    private long currencyId;
    private boolean isPublic;
    private boolean isDefault;

    private boolean status;
    private LocalDateTime cDate;
    private LocalDateTime uDate;
    private long cUser;
    private long uUser;

}
