package com.elipcero.springdata.repositories.base;

/**
 * Adds new features for invoker
 */
public interface RepositoryExtensionMethodInvoker {

	/**
	 * Exists generic
	 *
	 * @param id the id
	 * @return true when exists
	 */
	Boolean exists(Object id);
}
