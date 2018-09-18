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
public class AccountNoTransferRequest extends TransferRequest {
    private String accountNo;
    private String bic;
    private double amount;
}
