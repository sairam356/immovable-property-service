package com.immovable.propertyservice.repo;

import com.immovable.propertyservice.entities.WalletTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends MongoRepository<WalletTransaction, String> {
}
