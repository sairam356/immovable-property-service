package com.immovable.propertyservice.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.entities.WalletTransaction;
import com.immovable.propertyservice.enums.ResourceType;
import com.immovable.propertyservice.enums.TransactionType;
import com.immovable.propertyservice.exception.BadRequestException;
import com.immovable.propertyservice.exception.ResourceNotFoundException;
import com.immovable.propertyservice.repo.WalletRepository;
import com.immovable.propertyservice.repo.WalletTransactionRepository;
import com.immovable.propertyservice.utils.BigDecimalUtils;

import lombok.extern.slf4j.Slf4j;

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
		if (!CollectionUtils.isEmpty(walletInfo.getWalletTransactions())) {
			updateWalletBalance(walletInfo, walletTransaction);
		}
		WalletTransaction tranObj = walletTransactionRepository.save(walletTransaction);
		walletTransactions.add(tranObj);
		walletRepository.save(walletInfo);

		return tranObj;
	}

	private void updateWalletBalance(Wallet walletInfo, WalletTransaction walletTransaction) {
		if (BigDecimalUtils.checkIfPositiveNonZero(walletTransaction.getAmount())) {
			if (walletTransaction.getTransactionType().name().equalsIgnoreCase(TransactionType.DEPOSIT.name())) {
				walletInfo.setBalance(walletInfo.getBalance().add(walletTransaction.getAmount()));
			} else {
				walletInfo.setBalance(walletInfo.getBalance().subtract(walletTransaction.getAmount()));
			}
		}else {
			throw new BadRequestException("Amount is invalid");
		}
	}

	@Override
	public Wallet getWalletInfo(String walletId) {
		return walletRepository.findById(walletId)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceType.WALLET));
	}
}
