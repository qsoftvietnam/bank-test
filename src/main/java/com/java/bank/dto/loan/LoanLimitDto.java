package com.java.bank.dto.loan;

import com.java.bank.entity.LoanType;
import com.java.bank.type.LoanTypeConstants;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanLimitDto {
    private BigDecimal totalLimit;
    private BigDecimal totalLimitRemain;
    private LoanTypeConstants loanType;
}
