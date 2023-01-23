package com.immovable.investmentplatform.repos;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.Wallet;

@Repository
public interface WalletRepository extends MongoRepository<Wallet,String> {
    Optional<Wallet> findByCustomerId(String customerId);
}
