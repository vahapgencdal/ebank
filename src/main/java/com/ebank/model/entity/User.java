package com.ebank.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String profile;
    private String addressId;

}
