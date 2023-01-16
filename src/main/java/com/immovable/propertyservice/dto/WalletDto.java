package com.immovable.propertyservice.dto;

import com.immovable.propertyservice.entities.WalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private String customerId;

    private List<WalletTransaction> walletTransactions;

    private BigDecimal balance = BigDecimal.ZERO;

}
