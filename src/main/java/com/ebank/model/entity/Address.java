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
public class Address {
    private long id;
    private String country;
    private String postcode;
    private String city;
    private String state;
    private String street;

    private boolean status;//ACTIVE OR PASSIVE
    private LocalDateTime cDate;
    private LocalDateTime uDate;
}