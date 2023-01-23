package com.immovable.investmentplatform.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.CartItems;


@Repository
public interface CartItemsRepository extends MongoRepository<CartItems, String> {

    List<CartItems> findByPropertyId(String status);
    
   List<CartItems> findByStatus(String status);
}
