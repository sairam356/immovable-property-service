package com.immovable.investmentplatform.dto;

import java.math.BigDecimal;
import java.util.List;

import com.immovable.investmentplatform.entities.WalletTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private String customerId;

    private List<WalletTransaction> walletTransactions;

    private BigDecimal balance = BigDecimal.ZERO;

}
