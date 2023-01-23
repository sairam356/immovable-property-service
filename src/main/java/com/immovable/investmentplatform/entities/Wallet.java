package com.immovable.investmentplatform.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wallet")
public class Wallet {
    @Id
    private String id;

    private String customerId;

    @DBRef
    private List<WalletTransaction> walletTransactions;

    private BigDecimal balance = BigDecimal.ZERO;

    @CreatedDate
    private LocalDateTime createdDate;
}
