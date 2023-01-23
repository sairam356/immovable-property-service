package com.immovable.investmentplatform.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.immovable.investmentplatform.enums.TransactionStatus;
import com.immovable.investmentplatform.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wallet_transactions")
public class WalletTransaction {
    @Id
    private String id;

    private String walletId;

    private String transactionId;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private BigDecimal amount = BigDecimal.ZERO;

    @CreatedDate
    private LocalDateTime createdDate;
}
