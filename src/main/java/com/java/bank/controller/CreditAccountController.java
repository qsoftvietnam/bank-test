package com.java.bank.controller;

import com.java.bank.dto.creditAccount.CreateCreditAccountDto;
import com.java.bank.dto.response.ResponseDto;
import com.java.bank.service.CreditAccountService;
import lombok.AllArgsConstructor;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/credit")
public class CreditAccountController {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final @NonNull CreditAccountService creditAccountService;

    @PostMapping("")
    public ResponseEntity<ResponseDto> createCreditFacility(@RequestBody CreateCreditAccountDto request) {
        logger.info("[createCreditFacility]: Search credit facility based on the request = {}", request);
        ResponseDto responseDto = new ResponseDto();

        try {
            responseDto.setData(creditAccountService.createCreditFacility(request));
            responseDto.setMessage("Success");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }
//
//    @PostMapping("/search")
//    public ResponseEntity<?> searchCreditAccount(@RequestBody SearchCreditAccountDto request) throws BankException {
//        logger.info("[searchCreditAccount]: Search credit accounts based on the request = {}", request);
//        Page<CreditFacility> creditAccountList;
//        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize());
//        if (request.getCsn() == null) {
//            creditAccountList = creditAccountRepository.findAll(pageable);
//        } else {
//            Optional<User> existingUser = userRepository.findUserByCsn(request.getCsn());
//            if(existingUser.isEmpty()) {
//                throw new BankException(ExceptionCode.USER_WITH_ID_NOT_EXIST);
//            }
//            creditAccountList = creditAccountRepository.findAllByCsn(request.getCsn(), pageable);
//        }
//
//        return ResponseEntity.ok(creditAccountList);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCreditAccountById(@PathVariable Integer id) throws BankException {
//        logger.info("[getCreditAccountById]: Get credit account with id = {}", id);
//        Optional<CreditFacility> existingCreditAccount = creditAccountRepository.findById(id);
//        if (existingCreditAccount.isEmpty()) {
//            throw new BankException(ExceptionCode.CREDIT_ACCOUNT_WITH_ID_NOT_EXIST);
//        }
//
//        return ResponseEntity.ok(existingCreditAccount.get());
//    }
}
