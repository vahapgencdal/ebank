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
 * @description :Allow Cor Requests
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class User {

    private long id;
    private long bankId;
    private String fullName;
    private String phone;
    private String email;
    private long addressId;
    private String userType;
    private boolean status;
    private LocalDateTime cDate;
    private LocalDateTime uDate;
    private long cUser;
    private long uUser;

}
