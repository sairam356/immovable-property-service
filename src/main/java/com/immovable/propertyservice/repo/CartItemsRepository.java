package com.immovable.propertyservice.repo;

import com.immovable.propertyservice.entities.CartItems;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemsRepository extends MongoRepository<CartItems, String> {

    List<CartItems> findByPropertyId(String status);
    
   List<CartItems> findByStatus(String status);
}
