package com.java.bank.controller;

import com.java.bank.dto.payment.CreatePaymentDto;
import com.java.bank.dto.response.ResponseDto;
import com.java.bank.entity.Payment;
import com.java.bank.exception.BankException;
import com.java.bank.repository.LoanRepository;
import com.java.bank.repository.PaymentRepository;
import com.java.bank.repository.UserRepository;
import com.java.bank.service.PaymentService;
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

import java.util.UUID;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private @NonNull PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<ResponseDto> createTransaction(@RequestBody CreatePaymentDto request) {
        logger.info("[createTransaction]: Create payment with csn ");
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(paymentService.createPayment(request));
            responseDto.setMessage("Success");
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/loan")
    public ResponseEntity<ResponseDto> getLoanPayments(@RequestParam UUID loanId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        logger.info("[searchLoan]: Search loans based on the request");
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(paymentService.getLoanPayments(loanId, page, size));
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseDto);
        }
    }
}
