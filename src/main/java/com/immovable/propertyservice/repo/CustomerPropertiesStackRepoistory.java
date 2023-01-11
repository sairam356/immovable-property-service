package com.immovable.propertyservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.propertyservice.entities.CustomerPropertiesStack;
@Repository
public interface CustomerPropertiesStackRepoistory  extends MongoRepository<CustomerPropertiesStack, String> {

}
