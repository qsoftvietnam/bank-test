package com.java.bank.dto.creditAccount;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateCreditAccountDto {
    private String email;
    private BigDecimal totalLimit;
    private String currency;
    private Date startDate;
    private Date endDate;
}
