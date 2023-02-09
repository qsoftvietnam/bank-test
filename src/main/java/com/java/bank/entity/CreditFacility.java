package com.java.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.bank.type.StatusCode;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "tbl_credit_facility")
public class CreditFacility {
    @Id
    @GeneratedValue()
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusCode status;

    @Column(name = "total_limit")
    private BigDecimal totalLimit;

    @Column(name = "currency")
    private String currency;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "creditFacility")
    private Set<Loan> loans = new HashSet<>();
}
