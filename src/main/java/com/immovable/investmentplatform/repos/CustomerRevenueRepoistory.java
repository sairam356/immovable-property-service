package com.immovable.investmentplatform.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.CustomerRevenue;
@Repository
public interface CustomerRevenueRepoistory extends MongoRepository<CustomerRevenue, String> {

}
