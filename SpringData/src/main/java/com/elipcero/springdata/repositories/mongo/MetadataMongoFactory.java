package com.elipcero.springdata.repositories.mongo;

import java.io.Serializable;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;

class MetadataMongoFactory {
	
	public static MetadataMongoFactory Build(MongoOperations mongoOperations) {
		return new MetadataMongoFactory(mongoOperations.getConverter().getMappingContext());
	}
	
	private final MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext;
	
	public MetadataMongoFactory(MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
		this.mappingContext = mappingContext;
	}
	
	@SuppressWarnings("unchecked")
	public <T, ID extends Serializable> MongoEntityInformation<T, ID> getEntityInformation(Class<?> domainType) {
		return new MappingMongoEntityInformation<T, ID>((MongoPersistentEntity<T>)this.getMongoPersistentEntity(domainType));
	}
	
	public MongoPersistentEntity<?> getMongoPersistentEntity(Class<?> domainType) {
		return mappingContext.getPersistentEntity(domainType);
	}
	
	public MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> getMappingContext() {
		return this.mappingContext;
	}
}
