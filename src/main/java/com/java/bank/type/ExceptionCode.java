package com.java.bank.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //User
    USER_WITH_EMAIL_ALREADY_EXIST(1000, "The user with email has already existed"),
    USER_WITH_EMAIL_NOT_EXIST(1001, "The user with email has already existed"),
    USER_WITH_USERNAME_ALREADY_EXIST(1002, "The user with email has already existed"),
    USER_WITH_ID_NOT_EXIST(1003, "The user with id does not exist"),

    //Loan
    LOAN_WITH_ID_NOT_EXIST(2000, "The loan with id does not exist"),
    LOANS_WITH_ID_NOT_EXIST(2001, "The loan list with id does not exist"),
    LOAN_LIMIT_NOT_ENOUGH(2002, "The loan amount surpasses the loan limit for this loan type"),
    LOAN_LIMIT_REMAIN_NOT_ENOUGH(2003, "The remaining limit for this loan type is not enough to create a new loan"),
    LOAN_CLOSED(2004, "This loan was closed"),


    //Credit account
    CREDIT_ACCOUNT_WITH_ID_NOT_EXIST(3000, "The credit account with id does not exist"),
    CREDIT_LIMIT_REMAIN_NOT_ENOUGH(3001, "The limit remain for this credit facility is not enough to create a new loan"),

    //Loan type
    LOAN_TYPE_NOT_EXIST(4000, "The loan type does not exist");


    private final Integer code;
    private final String message;
}
