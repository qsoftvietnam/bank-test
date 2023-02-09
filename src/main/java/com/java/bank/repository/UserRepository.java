package com.java.bank.repository;

import com.java.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String userName);
//    Optional<User> findUserByIdAndDeleted(Integer id, boolean deleted);
//    Optional<User> findUserByCsn(Integer csn);
}
