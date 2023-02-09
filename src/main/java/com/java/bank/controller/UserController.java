package com.java.bank.controller;

import com.java.bank.dto.response.ResponseDto;
import com.java.bank.dto.user.CreateUserDto;
import com.java.bank.dto.user.UserResponseDto;
import com.java.bank.entity.Role;
import com.java.bank.entity.User;
import com.java.bank.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private @NonNull UserService userService;

    @PostMapping("/applicant")
    public ResponseEntity<ResponseDto> createUser(@RequestBody CreateUserDto request) {
        logger.info("[createUser]: Create a new applicant with email = {} and username = {}", request.getEmail(), request.getUsername());
        ResponseDto responseDto = new ResponseDto();
        UserResponseDto userResponseDto = new UserResponseDto();

        try {
            User newApplicant = userService.createApplicant(request);
            userResponseDto.setUsername(newApplicant.getUsername());
            userResponseDto.setEmail(newApplicant.getEmail());
            userResponseDto.setPhoneNumber(newApplicant.getPhoneNumber());
            userResponseDto.setAddress(newApplicant.getAddress());
            userResponseDto.setRoles(newApplicant.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));

            responseDto.setData(userResponseDto);
            responseDto.setMessage("Success");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

//    @PostMapping("/search")
//    public ResponseEntity<?>  searchUser(@RequestBody SearchUserDto request) {
//        logger.info("[searchUser]: Search users based on the request = {}", request);
//        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize());
//        Page<User> userList = userRepository.findAll(pageable);
//        return ResponseEntity.ok(userList);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?>  getUserWithId(@PathVariable Integer id) throws Exception {
//        logger.info("[getUserWithId]: Get user with id = {}", id);
//        Optional<User> existingUser = userRepository.findById(id);
//        if (existingUser.isEmpty()) {
//            throw new BankException(ExceptionCode.USER_WITH_ID_NOT_EXIST);
//        }
//        return ResponseEntity.ok(existingUser.get());
//    }
}
