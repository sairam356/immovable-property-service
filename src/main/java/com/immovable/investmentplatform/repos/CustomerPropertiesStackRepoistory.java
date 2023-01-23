package com.immovable.investmentplatform.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.CustomerPropertiesStack;

@Repository
public interface CustomerPropertiesStackRepoistory  extends MongoRepository<CustomerPropertiesStack, String> {

	@Query("{customerId :?0}")
	List<CustomerPropertiesStack> findAllByCustomerId(String customerId);
	
	  List<CustomerPropertiesStack> findByPropertyIdAndCustomerIdAndStatus(String propertyId,String customerId ,String status);
	  

}
