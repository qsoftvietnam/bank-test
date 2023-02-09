package com.java.bank.repository;

import com.java.bank.entity.LoanType;
import com.java.bank.type.LoanTypeConstants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanTypeRepository extends JpaRepository<LoanType, UUID> {
    Optional<LoanType> findByLoanType(LoanTypeConstants loanType);
}
