package com.immovable.propertyservice.services;

import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.entities.WalletTransaction;

public interface WalletService {
    Wallet save(Wallet wallet);

    WalletTransaction saveTransaction(WalletTransaction walletTransaction);

    Wallet getWalletInfo(String walletId);
}
