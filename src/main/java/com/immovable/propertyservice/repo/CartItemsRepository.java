package com.immovable.propertyservice.repo;

import com.immovable.propertyservice.entities.CartItems;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemsRepository extends MongoRepository<CartItems, String> {

    CartItems findByPropertyId(String status);
}
