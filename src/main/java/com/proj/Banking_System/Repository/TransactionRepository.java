package com.proj.Banking_System.Repository;

import com.proj.Banking_System.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
