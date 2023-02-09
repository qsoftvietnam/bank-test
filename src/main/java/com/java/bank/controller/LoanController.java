package com.java.bank.controller;

import com.java.bank.dto.loan.CreateLoanDto;
import com.java.bank.dto.response.ResponseDto;
import com.java.bank.entity.Loan;
import com.java.bank.exception.BankException;
import com.java.bank.repository.LoanRepository;
import com.java.bank.service.LoanService;
import com.java.bank.type.ExceptionCode;
import com.java.bank.type.LoanTypeConstants;
import com.java.bank.type.StatusCode;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/loan")
public class LoanController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private @NonNull LoanRepository loanRepository;
    private @NonNull LoanService loanService;

    @PostMapping("")
    public ResponseEntity<ResponseDto> createLoan(@RequestBody CreateLoanDto request) {
        logger.info("[createLoan]: Create a new loan for user with");
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(loanService.createLoan(request));
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> searchLoan(@RequestParam(required = false) UUID userId,
                                                  @RequestParam(required = false) UUID creditFacilityId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        logger.info("[searchLoan]: Search loans based on the request");
        Page<Loan> loanList;
        Pageable pageable = PageRequest.of(page, size);
        ResponseDto responseDto = new ResponseDto();

        try {
            if (userId != null) {
                loanList = loanRepository.findAllByUserId(userId, pageable);
            } else if (creditFacilityId != null) {
                loanList = loanRepository.findAllByCreditFacility_Id(creditFacilityId, pageable);
            } else {
                loanList = loanRepository.findAll(pageable);
            }
            responseDto.setData(loanList);
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<ResponseDto> getLoanTotal(@RequestParam UUID userId,
                                                    @RequestParam (required = false) StatusCode status) {
        logger.info("[searchLoan]: Search loans based on the request");
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(loanService.getTotalLoan(userId, status));
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

    @GetMapping("/limit")
    public ResponseEntity<ResponseDto> getLoanLimitByType(@RequestParam UUID creditFacilityId, @RequestParam LoanTypeConstants loanType) {
        logger.info("[searchLoan]: Search loans based on the request");
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(loanService.getLoanLimitByType(creditFacilityId, loanType));
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getLoanById(@PathVariable UUID id) throws BankException {
        logger.info("[getLoanById]: Get loan with id = {}", id);
        Optional<Loan> existingLoan = loanRepository.findById(id);
        if (existingLoan.isEmpty()) {
            throw new BankException(ExceptionCode.LOAN_WITH_ID_NOT_EXIST);
        }

        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(existingLoan.get());
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }
}
