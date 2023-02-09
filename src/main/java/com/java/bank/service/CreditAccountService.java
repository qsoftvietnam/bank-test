package com.java.bank.service;

import com.java.bank.dto.creditAccount.CreateCreditAccountDto;
import com.java.bank.entity.CreditFacility;
import com.java.bank.entity.User;
import com.java.bank.exception.BankException;
import com.java.bank.repository.CreditFacilityRepository;
import com.java.bank.repository.UserRepository;
import com.java.bank.type.ExceptionCode;
import com.java.bank.type.StatusCode;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditAccountService {

    private @NonNull CreditFacilityRepository creditAccountRepository;
    private @NonNull UserRepository userRepository;


    public CreditFacility createCreditFacility(CreateCreditAccountDto request) throws BankException {
        CreditFacility creditFacility = new CreditFacility();

        Optional<User> applicant = userRepository.findUserByEmail(request.getEmail());

        if (applicant.isEmpty()) {
            throw new BankException(ExceptionCode.USER_WITH_ID_NOT_EXIST);
        }

        creditFacility.setCreatedAt(new Date());
        creditFacility.setUpdatedAt(new Date());
        creditFacility.setStatus(StatusCode.OPEN);
        creditFacility.setTotalLimit(request.getTotalLimit());
        creditFacility.setCurrency(request.getCurrency());
        creditFacility.setStartDate(request.getStartDate());
        creditFacility.setEndDate(request.getEndDate());
        creditFacility.setUser(applicant.get());

        return creditAccountRepository.save(creditFacility);
    }
}
