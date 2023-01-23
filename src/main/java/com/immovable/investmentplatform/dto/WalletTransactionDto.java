package com.immovable.investmentplatform.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.immovable.investmentplatform.enums.TransactionStatus;
import com.immovable.investmentplatform.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDto {
    private String customerId;

    private String walletId;

    private String transactionId;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private BigDecimal amount = BigDecimal.ZERO;

    @CreatedDate
    private LocalDateTime createdDate;
}