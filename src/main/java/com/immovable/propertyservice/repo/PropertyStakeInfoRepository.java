package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.propertyservice.entities.PropertyStakeInfo;

public interface PropertyStakeInfoRepository  extends MongoRepository<PropertyStakeInfo, String>{

}
