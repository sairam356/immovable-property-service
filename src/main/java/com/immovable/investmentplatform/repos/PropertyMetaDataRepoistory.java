package com.immovable.investmentplatform.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.investmentplatform.entities.PropertyMetaData;

public interface PropertyMetaDataRepoistory extends MongoRepository<PropertyMetaData, String> {

}
