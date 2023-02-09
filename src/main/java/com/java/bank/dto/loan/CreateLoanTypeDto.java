package com.java.bank.dto.loan;

import com.java.bank.type.LoanTypeConstants;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateLoanTypeDto {
    private LoanTypeConstants loanType;
    private BigDecimal loanTotalLimit;
}
