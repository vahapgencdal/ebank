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
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Account {

    private long id;
    private String userId;
    private String accountType;
    private String name;
    private String accountNo;
    private String iban;
    private BigDecimal balance;
    private String currency;
    private boolean isPublic;

    private boolean status;//ACTIVE OR PASSIVE
    private LocalDateTime cDate;
    private LocalDateTime uDate;

}
