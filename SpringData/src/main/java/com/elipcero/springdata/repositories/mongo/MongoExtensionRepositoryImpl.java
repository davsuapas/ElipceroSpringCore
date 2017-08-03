package com.elipcero.springdata.repositories.mongo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

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
public class MongoExtensionRepositoryImpl<T, ID extends Serializable>
	extends SimpleMongoRepository<T, ID> implements MongoExtensionRepository<T, ID> {
	
	private final MongoEntityInformation<T, ID> entityInformation;
	private final MongoOperations mongoOperations;
	
	private final MetadataMongoFactory metadata;

	/**
	 * Instantiates a new mongo extension repository impl.
	 *
	 * @param entityInformation the entity information
	 * @param mongoOperations the mongo operations
	 */
	public MongoExtensionRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
	    super(entityInformation, mongoOperations);
	    
	    this.entityInformation = entityInformation;
	    this.mongoOperations = mongoOperations;
	    
	    this.metadata = MetadataMongoFactory.Build(mongoOperations);
	}
	
	/* (non-Javadoc)
	 * @see com.elipcero.springdata.repositories.mongo.MongoAllSharedRepository#mergeEmbeddedRelation(java.io.Serializable)
	 */
	public void mergeEmbeddedRelation(T entity, String propertyPath) {
	
		MongoPersistentProperty propertyEmbedded =
				this.metadata.getMappingContext().getPersistentPropertyPath(
						propertyPath, this.entityInformation.getJavaType()).getBaseProperty();
		
					
		MetadataEmbddedRelation metadataEmbedded = this.BuildMetadataEmbddedRelation(propertyEmbedded);
		
		Iterable<?> iterableEmbedded = metadataEmbedded.getEmbeddedPropertyValue(entity);

		if (iterableEmbedded != null) {
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
	}
	
	/* (non-Javadoc)
	 * @see com.elipcero.springdata.repositories.mongo.MongoExtensionRepository#updateNoNulls(java.lang.Object)
	 */
	public T updateNoNulls(T entity) {
		
		Update update = new Update();
		
		ConversionService converts = mongoOperations.getConverter().getConversionService();
		
		UpdateAnyField updateAnyField = new UpdateAnyField(); // it's used UpdateAnyField because local variable cannot be used in closed method
	
		this.metadata.getMongoPersistentEntity(this.entityInformation.getJavaType()).doWithProperties(
			(MongoPersistentProperty prop) -> {
				if (prop.getType() == Optional.class) {
					try {
						Optional<?> value = (Optional<?>)prop.getGetter().invoke(entity);
						if (value != null && value.isPresent()) {
							update.set(prop.getFieldName(), converts.convert(value, prop.getType()));
							updateAnyField.setValue(true);
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new MongoExtensionReflectionError();
					}
				}
			}
		);

		if (updateAnyField.isUpdated()) {
			Boolean updated = this.mongoOperations.updateFirst(
					new Query(where(this.entityInformation.getIdAttribute()).is(this.entityInformation.getId(entity))),
					update,
					this.entityInformation.getJavaType()).isUpdateOfExisting();
			if (updated) 
				return entity;
			else 
				return null;
		}
		else 
			return null;
	}
	
	// I don't use exists method because using "exists" getting all fields of the document
	// I use findone to get only the key information, in this way the query is more yield
	public Boolean exists(Object id) {
		Query query = new Query(where(entityInformation.getIdAttribute()).is(id));
		query.fields().include(this.entityInformation.getIdAttribute());
		
		return this.mongoOperations.findOne(query, this.entityInformation.getJavaType()) != null;
	}
		
	private MetadataEmbddedRelation BuildMetadataEmbddedRelation(MongoPersistentProperty propertyEmbedded) {
		MongoPersistentProperty embeddedPropertyEntityId = this.metadata.getMongoPersistentEntity(propertyEmbedded.getActualType()).getIdProperty();
		
		if (embeddedPropertyEntityId == null) {
			throw new MongoExtensionPropertyIdNotFoundException();
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
		
		public ObjectId getEmbeddedIdValue(Object embeddedEntity) {
			try {
				return this.converter.convert(this.embeddedPropertyEntityId.getGetter().invoke(embeddedEntity), ObjectId.class);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new MongoExtensionReflectionError();
			}
		}
		
		public Iterable<?> getEmbeddedPropertyValue(Object entity) {
			try {
				return (Iterable<?>)this.embeddedProperty.getGetter().invoke(entity);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new MongoExtensionReflectionError();
			}
		}

		public String getEmbeddedPropertyFieldName() {
			return this.embeddedProperty.getFieldName();
		}
	}
	
	private class UpdateAnyField {
		
		public UpdateAnyField() {
			this.value = false;
		}
		
		private Boolean value;
		
		public Boolean isUpdated() {
			return this.value;
		}
		
		public void setValue(Boolean value) {
			this.value = value;
		}
	}
}