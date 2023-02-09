package com.java.bank.repository;

import com.java.bank.entity.Loan;
import com.java.bank.entity.LoanType;
import com.java.bank.type.LoanTypeConstants;
import com.java.bank.type.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    Page<Loan> findAllById (UUID id, Pageable pageable);
    Page<Loan> findAllByUserId (UUID userId, Pageable pageable);
    List<Loan> findAllByUserId (UUID userId);
    List<Loan> findAllByUserIdAndStatus(UUID userId, StatusCode statusCode);
    Page<Loan> findAllByCreditFacility_Id (UUID creditFacilityId, Pageable pageable);

    List<Loan> findAllByIdAndLoanTypeAndStatus (UUID id, LoanType loanType, StatusCode status);
    List<Loan> findAllByLoanType(LoanType loanType);

    List<Loan> findAllByLoanTypeAndStatusAndCreditFacility_Id(LoanType loanType, StatusCode status, UUID creditFacilityId);
    List<Loan> findAllByCreditFacility_IdAndStatus(UUID creditFacilityId, StatusCode statusCode);
}
