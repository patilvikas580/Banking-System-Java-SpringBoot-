package com.proj.Banking_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrediDebitRequest {
    private String accountNumber;
    private BigDecimal amount;
}
