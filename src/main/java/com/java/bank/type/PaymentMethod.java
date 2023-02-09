package com.java.bank.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethod {
    CASH("cash"),
    TRANSFER("transfer");

    private final String name;
}
