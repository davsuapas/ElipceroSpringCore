package com.elipcero.springdata.repositories.mongo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author dsuarez
 * 
 * Interface for sharing behavior for embedded relation in mongodb.
 *
 */

@NoRepositoryBean
public interface MongoEmbeddedRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {
  
  /**
   * Merge the items of the relation embedded. 
   * It is required that the items of relation embedded must have unique identifier (objectId)
   * The unique identifier must marked with @Id 
   * If the items exist then update item and the items don't exist then add item 
   *
   * @param entity the main entity where is the embedded relation
   * @param propertyPath the relation embedded property name. This property must return a list
   * @param embeddedRelationType the embedded property type.
   */
	<TEmbedded> void mergeEmbeddedRelation(T entity, String propertyPath, Class<TEmbedded> embeddedRelationType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}

