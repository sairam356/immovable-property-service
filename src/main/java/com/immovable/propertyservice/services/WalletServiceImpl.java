package com.immovable.propertyservice.services;

import com.immovable.propertyservice.enums.ResourceType;
import com.immovable.propertyservice.dto.WalletTransactionDto;
import com.immovable.propertyservice.entities.Wallet;
import com.immovable.propertyservice.entities.WalletTransaction;
import com.immovable.propertyservice.enums.TransactionType;
import com.immovable.propertyservice.exception.BadRequestException;
import com.immovable.propertyservice.exception.ResourceNotFoundException;
import com.immovable.propertyservice.repo.WalletRepository;
import com.immovable.propertyservice.repo.WalletTransactionRepository;
import com.immovable.propertyservice.utils.BigDecimalUtils;
import com.immovable.propertyservice.utils.CompareOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
		WalletTransaction walletTransactionDto = wallet.getWalletTransactions().get(0);
		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setTransactionStatus(walletTransactionDto.getTransactionStatus());
		walletTransaction.setTransactionType(walletTransactionDto.getTransactionType());
		walletTransaction.setAmount(walletTransactionDto.getAmount());
		walletTransaction.setWalletId(wallet.getId());
		walletTransaction.setTransactionId(UUID.randomUUID().toString());
		walletTransaction.setCreatedDate(LocalDateTime.now());
		if (!CollectionUtils.isEmpty(wallet.getWalletTransactions())) {
			updateWalletBalance(wallet, walletTransaction);
		}
		WalletTransaction tranObj = walletTransactionRepository.save(walletTransaction);
		wallet.setWalletTransactions(new ArrayList<>(Collections.singleton(tranObj)));
		wallet.setCreatedDate(LocalDateTime.now());
		return walletRepository.save(wallet);
	}

	@Override
	public WalletTransaction saveTransaction(WalletTransactionDto walletTransactionDto) {
		Wallet walletInfo = getWalletInfo(walletTransactionDto.getCustomerId());
		List<WalletTransaction> walletTransactions = walletInfo.getWalletTransactions();
		WalletTransaction walletTransaction = new WalletTransaction();
		walletTransaction.setTransactionStatus(walletTransactionDto.getTransactionStatus());
		walletTransaction.setTransactionType(walletTransactionDto.getTransactionType());
		walletTransaction.setAmount(walletTransactionDto.getAmount());
		walletTransaction.setWalletId(walletInfo.getId());
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
			if (walletTransaction.getTransactionType().name().equalsIgnoreCase(TransactionType.DEPOSIT.name()) || walletTransaction.getTransactionType().name().equalsIgnoreCase(TransactionType.SELL.name())) {
				walletInfo.setBalance(walletInfo.getBalance().add(walletTransaction.getAmount()));
			} else if (BigDecimalUtils.checkIf(walletTransaction.getAmount(), CompareOperator.LESS_THAN_OR_EQUALS.name(), walletInfo.getBalance()) && (walletTransaction.getTransactionType().name().equalsIgnoreCase(TransactionType.WITHDRAW.name())
					|| walletTransaction.getTransactionType().name().equalsIgnoreCase(TransactionType.BUY.name()))){
				walletInfo.setBalance(walletInfo.getBalance().subtract(walletTransaction.getAmount()));
			}else{
				throw new BadRequestException("Transaction amount is greater than wallet balance");
			}
		}else {
			throw new BadRequestException("Amount is invalid");
		}
	}

	@Override
	public Wallet getWalletInfo(String customerId) {
		return walletRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(ResourceType.WALLET));
	}

	@Override
	public Map<String, BigDecimal> getBalance(String customerId) {
		Map<String,BigDecimal> map = new HashMap<>();
		Wallet wallet = walletRepository.findByCustomerId(customerId).orElseThrow(() -> new ResourceNotFoundException(ResourceType.WALLET));
		map.put("balance",wallet.getBalance());
		return map;
	}
}
