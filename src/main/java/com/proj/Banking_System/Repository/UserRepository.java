package com.proj.Banking_System.Repository;

import com.proj.Banking_System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findUserByAccountNumber(String accountNumber);
}
