package com.java.bank.dto.loan;

import com.java.bank.type.LoanTypeConstants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
public class CreateLoanDto {
    private UUID creditFacilityId;
    private LoanTypeConstants loanType;
    private BigDecimal amount;
    private String currency;
    private Date startDate;
    private Date endDate;
    private Double interestRate;
}
