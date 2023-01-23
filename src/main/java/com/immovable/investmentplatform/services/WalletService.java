package com.immovable.investmentplatform.services;

import java.math.BigDecimal;
import java.util.Map;

import com.immovable.investmentplatform.dto.WalletDto;
import com.immovable.investmentplatform.dto.WalletTransactionDto;
import com.immovable.investmentplatform.entities.Wallet;
import com.immovable.investmentplatform.entities.WalletTransaction;

public interface WalletService {
    Wallet save(WalletDto walletDto);

    WalletTransaction saveTransaction(WalletTransactionDto walletTransaction);

    Wallet getWalletInfo(String walletId);

    Map<String, BigDecimal> getBalance(String customerId);
    
	public boolean isValidTransaction(WalletTransactionDto walletTransactionDto);
}
