package com.elipcero.springdatarest.integrationtest.webmvc;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.elipcero.springdata.repositories.mongo.MongoExtensionRepository;

@RepositoryRestResource(path = "domains")
public interface MongoRepository extends MongoExtensionRepository<MongoDomain, String> {
}
