package com.java.bank.service;

import com.java.bank.dto.loan.CreateLoanDto;
import com.java.bank.dto.loan.LoanLimitDto;
import com.java.bank.dto.loan.TotalLoanDto;
import com.java.bank.entity.CreditFacility;
import com.java.bank.entity.Loan;
import com.java.bank.entity.LoanType;
import com.java.bank.exception.BankException;
import com.java.bank.repository.CreditFacilityRepository;
import com.java.bank.repository.LoanRepository;
import com.java.bank.repository.LoanTypeRepository;
import com.java.bank.type.ExceptionCode;
import com.java.bank.type.LoanTypeConstants;
import com.java.bank.type.StatusCode;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class LoanService {

    private @NonNull LoanRepository loanRepository;
    private @NonNull CreditFacilityRepository creditFacilityRepository;
    private @NonNull LoanTypeRepository loanTypeRepository;

    public Loan createLoan(CreateLoanDto request) throws BankException {
        Optional<CreditFacility> existingCredit = creditFacilityRepository.findById(request.getCreditFacilityId());
        if (existingCredit.isEmpty()) {
            throw new BankException(ExceptionCode.CREDIT_ACCOUNT_WITH_ID_NOT_EXIST);
        }

        Optional<LoanType> loanType = loanTypeRepository.findByLoanType(request.getLoanType());
        if (loanType.isEmpty()) {
            throw new BankException(ExceptionCode.LOAN_TYPE_NOT_EXIST);
        }

        // If loan amount of loan type > total limit of loan type -> throw error
        int compareLimit = request.getAmount().compareTo(loanType.get().getLoanTotalLimit());
        if (compareLimit > 0) {
            throw new BankException(ExceptionCode.LOAN_LIMIT_NOT_ENOUGH);
        }

        // If total loan amount of loan type > total limit remaining of loan type -> throw error
        List<Loan> openLoanListByCreditAndType = loanRepository.findAllByLoanTypeAndStatusAndCreditFacility_Id(
            loanType.get(),
            StatusCode.OPEN,
            request.getCreditFacilityId()
        );

        BigDecimal totalCurrentLoanTypeAmount;
        if (openLoanListByCreditAndType.isEmpty()) {
            totalCurrentLoanTypeAmount = BigDecimal.valueOf(0);
        } else {
            totalCurrentLoanTypeAmount = openLoanListByCreditAndType
                .stream()
                .map(Loan::getAmount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        }

        BigDecimal totalLimitRemain = loanType.get().getLoanTotalLimit().subtract(totalCurrentLoanTypeAmount);
        int compareLimitRemain = request.getAmount().compareTo(totalLimitRemain);
        if (compareLimitRemain > 0) {
            throw new BankException(ExceptionCode.LOAN_LIMIT_REMAIN_NOT_ENOUGH);
        }

        // If total loan amount > total limit of credit facility -> throw error
        List<Loan> openLoanListByCredit = loanRepository.findAllByCreditFacility_IdAndStatus(
            request.getCreditFacilityId(),
            StatusCode.OPEN);

        BigDecimal totalCurrentLoansAmount;
        if (openLoanListByCredit.isEmpty()) {
            totalCurrentLoansAmount = BigDecimal.valueOf(0);
        } else {
            totalCurrentLoansAmount = openLoanListByCredit
                .stream()
                .map(Loan::getAmount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        }

        int compareCreditLimitRemain = request.getAmount().compareTo(existingCredit.get().getTotalLimit().subtract(totalCurrentLoansAmount));
        if (compareCreditLimitRemain > 0) {
            throw new BankException(ExceptionCode.CREDIT_LIMIT_REMAIN_NOT_ENOUGH);
        }

        // Create new loan
        int totalLoanYears = request.getEndDate().getYear() - request.getStartDate().getYear();
        Loan loan = new Loan();
        loan.setCreditFacility(existingCredit.get());
        loan.setCreatedAt(new Date());
        loan.setUpdatedAt(new Date());
        loan.setStatus(StatusCode.OPEN);
        loan.setAmount(request.getAmount());
        loan.setBalance(request.getAmount().add(request.getAmount().multiply(BigDecimal.valueOf(request.getInterestRate() * totalLoanYears))));
        loan.setCurrency(request.getCurrency());
        loan.setStartDate(request.getStartDate());
        loan.setEndDate(request.getEndDate());
        loan.setInterestRate(request.getInterestRate());
        loan.setLoanType(loanType.get());
        loan.setUser(existingCredit.get().getUser());

        return loanRepository.save(loan);
    }

    public TotalLoanDto getTotalLoan(UUID userId, StatusCode status) throws BankException {
        List<Loan> loanList;

        if (status == null) {
            loanList = loanRepository.findAllByUserId(userId);
        } else {
            loanList = loanRepository.findAllByUserIdAndStatus(userId, status);
        }


        BigDecimal totalLoanAmount = loanList.stream().map(Loan::getAmount).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        BigDecimal totalBalance = loanList.stream().map(Loan::getBalance).reduce(BigDecimal.valueOf(0), BigDecimal::add);

        TotalLoanDto totalLoanDto = new TotalLoanDto();
        totalLoanDto.setCurrency("USD");
        totalLoanDto.setTotalLoanAmount(totalLoanAmount);
        totalLoanDto.setTotalBalance(totalBalance);
        totalLoanDto.setNumberOfLoans(loanList.size());

        return totalLoanDto;
    }

    public LoanLimitDto getLoanLimitByType(UUID creditFacilityId, LoanTypeConstants loanType) throws BankException {
        BigDecimal totalLoanAmount;

        LoanLimitDto loanLimitDto = new LoanLimitDto();
        Optional<LoanType> loanTypeOptional = loanTypeRepository.findByLoanType(loanType);
        if (loanTypeOptional.isEmpty()) {
            throw new BankException(ExceptionCode.LOANS_WITH_ID_NOT_EXIST);
        }

        Optional<CreditFacility> existingCredit = creditFacilityRepository.findById(creditFacilityId);
        if (existingCredit.isEmpty()) {
            throw new BankException(ExceptionCode.CREDIT_ACCOUNT_WITH_ID_NOT_EXIST);
        }

        // TODO: Opened loan only
        List<Loan> loanList = loanRepository.findAllByLoanTypeAndStatusAndCreditFacility_Id(
            loanTypeOptional.get(),
            StatusCode.OPEN,
            creditFacilityId);
        BigDecimal totalLoanLimit = loanTypeOptional.get().getLoanTotalLimit();
        if (loanList.isEmpty()) {
            totalLoanAmount = BigDecimal.valueOf(0);
        } else {
            totalLoanAmount = loanList.stream().map(Loan::getAmount).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        }

        loanLimitDto.setTotalLimit(totalLoanLimit);
        loanLimitDto.setTotalLimitRemain(totalLoanLimit.subtract(totalLoanAmount));
        loanLimitDto.setLoanType(loanTypeOptional.get().getLoanType());

        return loanLimitDto;
    }
}
