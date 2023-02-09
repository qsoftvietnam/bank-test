package com.java.bank.entity;//package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.bank.type.PaymentMethod;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "tbl_payment")
public class Payment {
    @Id
    @GeneratedValue()
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "payment_amount")
    private BigDecimal paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_note")
    private String paymentNote;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
}
