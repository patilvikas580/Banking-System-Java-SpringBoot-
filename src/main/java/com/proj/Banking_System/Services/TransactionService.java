package com.proj.Banking_System.Services;

import com.proj.Banking_System.DTO.TransactionDto;
import com.proj.Banking_System.Entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
