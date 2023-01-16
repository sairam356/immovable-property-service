package com.immovable.propertyservice.services;

import com.immovable.propertyservice.dto.WalletTransactionDto;
import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.entities.WalletTransaction;

import java.math.BigDecimal;
import java.util.Map;

public interface WalletService {
    Wallet save(Wallet wallet);

    WalletTransaction saveTransaction(WalletTransactionDto walletTransaction);

    Wallet getWalletInfo(String walletId);

    Map<String, BigDecimal> getBalance(String customerId);
}
