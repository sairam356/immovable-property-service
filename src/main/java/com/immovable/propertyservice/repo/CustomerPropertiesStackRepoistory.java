package com.immovable.propertyservice.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.immovable.propertyservice.entities.CustomerPropertiesStack;

@Repository
public interface CustomerPropertiesStackRepoistory  extends MongoRepository<CustomerPropertiesStack, String> {

	@Query("{customerId :?0}")
	List<CustomerPropertiesStack> findAllByCustomerId(String customerId);

}
