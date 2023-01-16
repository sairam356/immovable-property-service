package com.immovable.propertyservice.dto;

import com.immovable.propertyservice.enums.TransactionStatus;
import com.immovable.propertyservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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