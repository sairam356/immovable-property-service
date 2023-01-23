package com.immovable.investmentplatform.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.Property;

@Repository
public interface PropertyRepoistory extends MongoRepository<Property, String>{
	
   List<Property> findByStatus(String status);
}
