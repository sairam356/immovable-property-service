package com.immovable.investmentplatform.repos;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    @Query
    Optional<Cart> findByCustomerIdAndStatus(String customerId, String status);

    @Query
    Optional<Cart> findByCustomerId(String customerId);
}
