package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.immovable.propertyservice.entities.CustomerPropertiesStack;

public interface CustomerPropertiesStackRepoistory  extends MongoRepository<CustomerPropertiesStack, String> {

}
