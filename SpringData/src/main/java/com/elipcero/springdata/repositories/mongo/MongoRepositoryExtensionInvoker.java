package com.elipcero.springdata.repositories.mongo;

import org.springframework.data.repository.support.RepositoryInvoker;

import com.elipcero.springdata.repositories.base.ExtensionRepository;
import com.elipcero.springdata.repositories.base.RepositoryExtensionInvoker;

public class MongoRepositoryExtensionInvoker extends RepositoryExtensionInvoker {
	
	private final MongoExtensionRepository<Object, ?> mongoExtensionRepository; 

	public MongoRepositoryExtensionInvoker(RepositoryInvoker invoker, MongoExtensionRepository<Object, ?> mongoExtensionRepository) {
		super(invoker, (ExtensionRepository<Object>)mongoExtensionRepository);
		
		this.mongoExtensionRepository = mongoExtensionRepository;
	}
	
	public void invokeMergeEmbedded(Object object, String propertyPath) {
		mongoExtensionRepository.mergeEmbeddedRelation(object, propertyPath);
	}
}
