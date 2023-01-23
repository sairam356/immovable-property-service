package com.immovable.investmentplatform.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.investmentplatform.entities.PropertyStakeInfo;

public interface PropertyStakeInfoRepository  extends MongoRepository<PropertyStakeInfo, String>{

}
