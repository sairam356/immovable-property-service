package com.immovable.propertyservice.repo;

import com.immovable.propertyservice.entities.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends MongoRepository<Wallet,String> {
}
