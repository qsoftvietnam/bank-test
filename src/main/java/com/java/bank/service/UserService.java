package com.java.bank.service;

import com.java.bank.dto.user.CreateUserDto;
import com.java.bank.entity.Role;
import com.java.bank.entity.User;
import com.java.bank.exception.BankException;
import com.java.bank.repository.RoleRepository;
import com.java.bank.repository.UserRepository;
import com.java.bank.type.ExceptionCode;
import com.java.bank.type.RoleConstants;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.java.bank.util.Util.generateRandomNumberWithLength;

@Service
@AllArgsConstructor
public class UserService {

    private @NonNull UserRepository userRepository;
    private @NonNull RoleRepository roleRepository;

    public User createApplicant(CreateUserDto request) throws BankException {
        Optional<User> existingApplicantWithEmail = userRepository.findUserByEmail(request.getEmail());
        if (existingApplicantWithEmail.isPresent()) {
            throw new BankException(ExceptionCode.USER_WITH_EMAIL_ALREADY_EXIST);
        }

        Optional<User> existingApplicantWithUsername = userRepository.findUserByUsername(request.getUsername());
        if (existingApplicantWithUsername.isPresent()) {
            throw new BankException(ExceptionCode.USER_WITH_USERNAME_ALREADY_EXIST);
        }

        User user = new User();
        Role role = roleRepository.findByRoleName(RoleConstants.APPLICANT).get();
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(role);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRoles(rolesSet);
        User savedUser = userRepository.save(user);

        roleRepository.save(role);

        return savedUser;
    }
}
