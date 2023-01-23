package com.immovable.investmentplatform.repos;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.immovable.investmentplatform.entities.UserEntity;


@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>{

	@Query("{username :?0}")
	Optional<UserEntity> findByUsername(String userName);
	
	@Query("{email :?0}")
	Optional<UserEntity> findByEmail(String email);

}
