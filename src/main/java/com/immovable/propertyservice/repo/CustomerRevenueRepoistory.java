package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.propertyservice.entities.CustomerRevenue;
@Repository
public interface CustomerRevenueRepoistory extends MongoRepository<CustomerRevenue, String> {

}
