package com.immovable.propertyservice.repo;

import com.immovable.propertyservice.entities.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    @Query
    Optional<Cart> findByCustomerIdAndStatus(String customerId, String status);
}
