package com.immovable.investmentplatform.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.WalletTransaction;

@Repository
public interface WalletTransactionRepository extends MongoRepository<WalletTransaction, String> {
}
