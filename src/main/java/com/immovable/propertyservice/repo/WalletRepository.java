package com.immovable.propertyservice.repo;

import com.immovable.propertyservice.entities.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet,String> {
    Optional<Wallet> findByCustomerId(String customerId);
}
