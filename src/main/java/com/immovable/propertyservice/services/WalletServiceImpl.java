package com.immovable.propertyservice.services;

import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.entities.WalletTransaction;
import com.immovable.propertyservice.repo.WalletRepository;
import com.immovable.propertyservice.repo.WalletTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletTransactionRepository walletTransactionRepository;

	@Override
	public Wallet save(Wallet wallet) {
		log.info("The wallet is being updated...");

		wallet.setWalletTransactions(Collections.emptyList());
		wallet.setCreatedDate(LocalDateTime.now());
		return walletRepository.save(wallet);
	}

	@Override
	public WalletTransaction saveTransaction(WalletTransaction walletTransaction) {

		Wallet walletInfo = getWalletInfo(walletTransaction.getWalletId());
		List<WalletTransaction> walletTransactions = walletInfo.getWalletTransactions();

		walletTransaction.setTransactionId(UUID.randomUUID().toString());
		walletTransaction.setCreatedDate(LocalDateTime.now());
		WalletTransaction tranObj = walletTransactionRepository.save(walletTransaction);
		walletTransactions.add(tranObj);
		walletRepository.save(walletInfo);

		return tranObj;
	}

	@Override
	public Wallet getWalletInfo(String walletId) {
		return walletRepository.findById(walletId)
				.orElseThrow(() -> new OpenApiResourceNotFoundException("Wallet info not available"));
	}
}
