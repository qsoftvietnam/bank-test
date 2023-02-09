package com.java.bank.dto.loan;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalLoanDto {
    private BigDecimal totalLoanAmount;
    private BigDecimal totalBalance;
    private int numberOfLoans;
    private String currency;
}
