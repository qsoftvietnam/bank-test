package com.java.bank.service;

import com.java.bank.dto.payment.CreatePaymentDto;
import com.java.bank.entity.Loan;
import com.java.bank.entity.Payment;
import com.java.bank.exception.BankException;
import com.java.bank.repository.LoanRepository;
import com.java.bank.repository.PaymentRepository;
import com.java.bank.type.ExceptionCode;
import com.java.bank.type.PaymentMethod;
import com.java.bank.type.StatusCode;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {

    private @NonNull LoanRepository loanRepository;
    private @NonNull PaymentRepository paymentRepository;

    public Payment createPayment(CreatePaymentDto request) throws BankException {
        Optional<Loan> existingLoan = loanRepository.findById(request.getLoanId());

        if (existingLoan.isEmpty()) {
            throw new BankException(ExceptionCode.LOAN_WITH_ID_NOT_EXIST);
        }

        if (existingLoan.get().getStatus() == StatusCode.CLOSED) {
            throw new BankException(ExceptionCode.LOAN_CLOSED);
        }

        BigDecimal balance = existingLoan.get().getBalance();
        BigDecimal remainingBalance = balance.subtract(request.getPaymentAmount());

        Payment payment = new Payment();
        payment.setPaymentAmount(request.getPaymentAmount());
        payment.setCreatedAt(new Date());
        payment.setUpdatedAt(new Date());
        payment.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        payment.setPaymentNote(request.getPaymentNote());
        payment.setLoan(existingLoan.get());

        existingLoan.get().setBalance(remainingBalance);
        existingLoan.get().setUpdatedAt(new Date());
        if (remainingBalance.compareTo(BigDecimal.ZERO) == 0) {
            existingLoan.get().setStatus(StatusCode.CLOSED);
        }
        loanRepository.save(existingLoan.get());

        return paymentRepository.save(payment);
    }

    public Page<Payment> getLoanPayments(UUID loanId, int page, int size) throws BankException {
        Page<Payment> payments;
        Pageable pageable = PageRequest.of(page, size);

        if (loanId == null) {
            payments = paymentRepository.findAll(pageable);
        } else {
            Optional<Loan> existingLoan = loanRepository.findById(loanId);

            if (existingLoan.isEmpty()) {
                throw new BankException(ExceptionCode.LOAN_WITH_ID_NOT_EXIST);
            }

            payments = paymentRepository.findAllByLoan_Id(loanId, pageable);
        }

        return payments;
    }
}
