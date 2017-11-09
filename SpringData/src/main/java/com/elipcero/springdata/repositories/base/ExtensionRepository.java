package com.elipcero.springdata.repositories.base;

/**
 * @author dsuarez
 * 
 * Interface for sharing behavior
 *
 */
public interface ExtensionRepository<T> {

	 /**
	   * Update the properties not nulls of the entity.
	   * The properties for researching must return an optional type or string
	   *
	   * @param entity for updating
	   */
		T updateNoNulls(T entity);
}
