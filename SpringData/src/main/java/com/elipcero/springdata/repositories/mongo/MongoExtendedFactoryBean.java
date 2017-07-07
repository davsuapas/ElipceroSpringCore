package com.elipcero.springdata.repositories.mongo;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author dsuarez
 * 
 * The class create factory for sharing behavior in mongodb.
 *
 */

public class MongoExtendedFactoryBean<R extends MongoRepository<T, I>, T, I extends Serializable> extends MongoRepositoryFactoryBean<R, T, I> {

	public MongoExtendedFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new MongoExtendedMongoRepositoryFactory<T,I>(operations);
	}
	
	private static class MongoExtendedMongoRepositoryFactory<T, ID extends Serializable> extends MongoRepositoryFactory {
	
		private final MongoOperations mongoOperations;
		
		public MongoExtendedMongoRepositoryFactory(MongoOperations mongoOperations) {
		    super(mongoOperations);
		    
		    this.mongoOperations = mongoOperations;
		}
		
		protected Object getTargetRepository(RepositoryInformation metadata) {
			
			if (isEmbeddedRepository(metadata.getRepositoryInterface())) {
			    return new MongoExtensionRepositoryImpl<T, ID>(
			    		MetadataMongoFactory.Build(mongoOperations).getEntityInformation(metadata.getDomainType()),
			    		mongoOperations
			    );
			} 
			else {
				return super.getTargetRepository(metadata);
			}
		}
		
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			
			if (isEmbeddedRepository(metadata.getRepositoryInterface())) {
				return MongoExtensionRepository.class;
			}
			else {
				return super.getRepositoryBaseClass(metadata);
			}
		}
		
		private static boolean isEmbeddedRepository(Class<?> repositoryInterface) {
			return MongoExtensionRepository.class.isAssignableFrom(repositoryInterface);
		}
	}
}