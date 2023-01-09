package com.immovable.propertyservice.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.immovable.propertyservice.entities.Property;

@Repository
public interface PropertyRepoistory extends MongoRepository<Property, String>{
	
   List<Property> findByStatus(String status);
}
