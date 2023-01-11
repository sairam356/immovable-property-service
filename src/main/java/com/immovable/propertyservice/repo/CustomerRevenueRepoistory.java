package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.propertyservice.entities.CustomerRevenue;

public interface CustomerRevenueRepoistory extends MongoRepository<CustomerRevenue, String> {

}
