package com.proj.Banking_System.Repository;

import com.proj.Banking_System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findUserByAccountNumber(String accountNumber);


}
