package com.elipcero.springdata.repositories.mongo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.bson.types.ObjectId;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

/**
 * The class for sharing behavior in mongodb.
 *
 */
public class MongoEmbeddedRepositoryImpl<T, ID extends Serializable>
	extends SimpleMongoRepository<T, ID> implements MongoEmbeddedRepository<T, ID> {
	
	private final MongoEntityInformation<T, ID> entityInformation;
	private final MongoOperations mongoOperations;
	
	private final MetadataMongoFactory metadata;

	public MongoEmbeddedRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
	    super(entityInformation, mongoOperations);
	    
	    this.entityInformation = entityInformation;
	    this.mongoOperations = mongoOperations;
	    
	    this.metadata = MetadataMongoFactory.Build(mongoOperations);
	}
	
	/* (non-Javadoc)
	 * @see com.elipcero.springdata.repositories.mongo.MongoAllSharedRepository#mergeEmbeddedRelation(java.io.Serializable)
	 */
	public <TEmbedded> void mergeEmbeddedRelation(T entity, String propertyPath, Class<TEmbedded> embeddedRelationType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		MongoPersistentProperty propertyEmbedded =
				this.metadata.getMappingContext().getPersistentPropertyPath(
						propertyPath, this.entityInformation.getJavaType()).getBaseProperty();
					
		MetadataEmbddedRelation metadataEmbedded = this.BuildMetadataEmbddedRelation(propertyEmbedded, embeddedRelationType);
		
		ID mainEntityId = this.entityInformation.getId(entity);
		
		Criteria criteriaId = where(this.entityInformation.getIdAttribute()).is(mainEntityId);
		
		for (Object reationItem : metadataEmbedded.getEmbeddedPropertyValue(entity)) {
			
			Criteria criteriaUpdate =
					where(this.entityInformation.getIdAttribute()).is(mainEntityId)
						.and(metadataEmbedded.getMetadataForId()).is(metadataEmbedded.getEmbeddedIdValue(reationItem));

			// the first update if exist. 
			if (!this.mongoOperations.updateFirst(
					new Query(criteriaUpdate),
					new Update().set(metadataEmbedded.getMetadataForList(), (Object)reationItem),
					this.entityInformation.getJavaType()).isUpdateOfExisting()) {
			
				// if don't exist add
				this.mongoOperations.updateFirst(
						new Query(criteriaId),
						new Update().addToSet(metadataEmbedded.getEmbeddedPropertyFieldName(), (Object)reationItem),
						this.entityInformation.getJavaType());
			}
		}
	}
	
	private MetadataEmbddedRelation BuildMetadataEmbddedRelation(MongoPersistentProperty propertyEmbedded, Class<?> embeddedRelationType) {
		MongoPersistentProperty embeddedPropertyEntityId = this.metadata.getMongoPersistentEntity(embeddedRelationType).getIdProperty();
		
		if (embeddedPropertyEntityId == null) {
			throw new MongoEmbeddedPropertyIdNotFoundException();
		}
		
		return new MetadataEmbddedRelation(propertyEmbedded, embeddedPropertyEntityId);
	}
	
	private class MetadataEmbddedRelation {
		
		private final MongoPersistentProperty embeddedProperty;
		private final MongoPersistentProperty embeddedPropertyEntityId;
		
		private final ConversionService converter;
				
		public MetadataEmbddedRelation(MongoPersistentProperty embeddedProperty, MongoPersistentProperty embeddedPropertyEntityId) {
			this.embeddedProperty = embeddedProperty;
			this.embeddedPropertyEntityId = embeddedPropertyEntityId;
			
			this.converter = mongoOperations.getConverter().getConversionService();
		}
		
		public String getMetadataForList() {
			return this.getEmbeddedPropertyFieldName() + ".$";
		}
		
		public String getMetadataForId() {
			return this.getEmbeddedPropertyFieldName() + "." + this.embeddedPropertyEntityId.getFieldName();
		}
		
		public ObjectId getEmbeddedIdValue(Object embeddedEntity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			return this.converter.convert(this.embeddedPropertyEntityId.getGetter().invoke(embeddedEntity), ObjectId.class);
		}
		
		public Iterable<?> getEmbeddedPropertyValue(Object entity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			return (Iterable<?>)this.embeddedProperty.getGetter().invoke(entity);
		}

		public String getEmbeddedPropertyFieldName() {
			return this.embeddedProperty.getFieldName();
		}
	}
}