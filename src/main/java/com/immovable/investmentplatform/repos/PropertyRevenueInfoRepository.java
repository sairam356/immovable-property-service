package com.immovable.investmentplatform.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.investmentplatform.entities.PropertyRevenueInfo;

public interface PropertyRevenueInfoRepository  extends MongoRepository<PropertyRevenueInfo, String>{

}
