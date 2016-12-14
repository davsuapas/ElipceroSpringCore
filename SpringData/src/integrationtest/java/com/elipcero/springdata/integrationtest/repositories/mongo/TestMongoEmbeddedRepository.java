package com.elipcero.springdata.integrationtest.repositories.mongo;

import com.elipcero.springdata.repositories.mongo.MongoEmbeddedRepository;

public interface TestMongoEmbeddedRepository extends MongoEmbeddedRepository<TestMongoEntity, String> {
}
