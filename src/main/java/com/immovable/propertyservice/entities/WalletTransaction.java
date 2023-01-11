package com.immovable.propertyservice.entities;

import com.immovable.propertyservice.enums.TransactionStatus;
import com.immovable.propertyservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private BigDecimal amount;

    @CreatedDate
    private LocalDateTime createdDate;
}
