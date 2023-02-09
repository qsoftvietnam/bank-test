package com.java.bank.exception;

import com.java.bank.type.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankException extends Exception {
    public BankException(ExceptionCode code) {
        super(String.format("%s: %s", code.getCode(), code.getMessage()));
    }
}
