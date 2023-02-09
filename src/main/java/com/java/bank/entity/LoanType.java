package com.java.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.bank.type.LoanTypeConstants;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "tbl_loan_type")
public class LoanType {
    @Id
    @GeneratedValue()
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_type")
    private LoanTypeConstants loanType;

    @Column(name = "loan_total_limit")
    private BigDecimal loanTotalLimit;

    @JsonIgnore
    @OneToMany(mappedBy = "loanType")
    private Set<Loan> loans = new HashSet<>();
}
