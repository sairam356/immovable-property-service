package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.propertyservice.entities.Customer;

public interface CustomerRepoistory extends MongoRepository<Customer, String>{

}
