package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.propertyservice.entities.Customer;
@Repository
public interface CustomerRepoistory extends MongoRepository<Customer, String>{

}
