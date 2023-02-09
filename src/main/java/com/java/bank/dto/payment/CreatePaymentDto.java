package com.java.bank.dto.payment;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreatePaymentDto {
    private UUID loanId;
    private BigDecimal paymentAmount;
    private String paymentMethod;
    private String paymentNote;
}
