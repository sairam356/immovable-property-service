package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.propertyservice.entities.Property;
import com.immovable.propertyservice.entities.PropertyRevenueInfo;

public interface PropertyRevenueInfoRepository  extends MongoRepository<PropertyRevenueInfo, String>{

}
