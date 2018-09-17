package com.ebank.model.request;

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
public class AccountTypeRequest {
    private long id;
    private String name;
    private String descr;
    private boolean status;
    private String cDate;
    private String uDate;
    private long cUser;
    private long uUser;
}
