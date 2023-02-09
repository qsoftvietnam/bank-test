package com.java.bank.controller;

import com.java.bank.dto.loan.CreateLoanTypeDto;
import com.java.bank.entity.LoanType;
import com.java.bank.repository.LoanTypeRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/loan-type")
public class LoanTypeController {

    private @NonNull LoanTypeRepository loanTypeRepository;

    @PostMapping("")
    public ResponseEntity<?> createRole(@RequestBody CreateLoanTypeDto request) {
        LoanType typeLimit = new LoanType();
        typeLimit.setLoanType(request.getLoanType());
        typeLimit.setLoanTotalLimit(request.getLoanTotalLimit());
        return ResponseEntity.ok(loanTypeRepository.save(typeLimit));
    }
}
