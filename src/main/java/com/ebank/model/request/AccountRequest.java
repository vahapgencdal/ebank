package com.ebank.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

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
public class AccountRequest {

    private long id;
    private String type;
    private String name;
    private String accountNo;
    private List<Amount> amounts;
    private String bank;
    private String user;


    public BigDecimal getAmountByCuurency(String currency) {
        for (int i = 0; i < amounts.size(); i++) {
            if (amounts.get(i).getCurrency().equals(currency)) return amounts.get(i).getAmount();
        }
        return null;
    }
}

