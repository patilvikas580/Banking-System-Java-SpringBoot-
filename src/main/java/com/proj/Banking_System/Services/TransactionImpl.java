package com.proj.Banking_System.Services;

import com.proj.Banking_System.DTO.TransactionDto;
import com.proj.Banking_System.Entity.Transaction;
import com.proj.Banking_System.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionImpl implements TransactionService {

    @Autowired
     TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder().transactionType(transactionDto.getTransactionType()).accountNumber(transactionDto.getAccountNumber()).amount(transactionDto.getAmount()).status("SUCCESS").build();
        transactionRepository.save(transaction);
        System.out.println("Transaction Saved Successfully");
    }

}
