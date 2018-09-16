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
public class CounterParty {
    private long id;
    private String accountNo;
    private String iban;
    private String type;//external or internal
    private String name;
    private String phone;
    private String email;
    private String country;
    private String profile;//CORPORATE OR PERSONAL
    private String currency;
    private boolean status;//ACTIVE OR PASSIVE
    private LocalDateTime cDate;
    private LocalDateTime uDate;

}