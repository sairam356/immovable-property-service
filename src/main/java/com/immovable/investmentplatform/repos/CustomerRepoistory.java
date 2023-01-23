package com.immovable.investmentplatform.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.Customer;
@Repository
public interface CustomerRepoistory extends MongoRepository<Customer, String>{
	
	List<Customer> findByUserId(String userId);

}
