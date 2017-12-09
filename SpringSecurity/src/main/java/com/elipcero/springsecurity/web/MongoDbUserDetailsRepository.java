package com.elipcero.springsecurity.web;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Mongo user details repository
 *
 * @author dav.sua.pas@gmail.com
 */
@RepositoryRestResource(exported = false)
public interface MongoDbUserDetailsRepository extends MongoRepository<MongoDbUserDetails, String> {

    /**
     * Find user by name
     * @param userName email
     * @return mongo user details
     */
	MongoDbUserDetails findByUserName(String userName);
}
