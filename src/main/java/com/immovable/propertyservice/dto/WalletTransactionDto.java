package com.immovable.propertyservice.dto;

import com.immovable.propertyservice.enums.TransactionStatus;
import com.immovable.propertyservice.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletTransactionDto {
    private String customerId;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private BigDecimal amount = BigDecimal.ZERO;
}