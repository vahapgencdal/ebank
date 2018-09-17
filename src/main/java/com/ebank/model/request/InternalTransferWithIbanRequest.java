package com.ebank.model.request;

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
public class InternalTransferWithIbanRequest {
    private long id;
    private String name;
    private String shrtCode;
    private BigDecimal fee;
    private String descr;

    private boolean status;//ACTIVE OR PASSIVE
    private LocalDateTime cDate;
    private LocalDateTime uDate;
}
