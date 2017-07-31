package com.elipcero.springdata.repositories.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.util.MultiValueMap;

public class RepositoryExtensionInvoker implements RepositoryInvoker {
	
	private final RepositoryInvoker invoker;
	private final ExtensionRepository<Object> repository;
	
	public RepositoryExtensionInvoker(RepositoryInvoker invoker, ExtensionRepository<Object> repository) {
		this.invoker = invoker;
		this.repository = repository;
	}
	
	public Object invokeUpdateNoNulls(Object object) {
		return repository.updateNoNulls(object);
	}

	@Override
	public boolean hasSaveMethod() {
		return invoker.hasSaveMethod();
	}

	@Override
	public boolean hasDeleteMethod() {
		return invoker.hasDeleteMethod();
	}

	@Override
	public boolean hasFindOneMethod() {
		return invoker.hasFindOneMethod();
	}

	@Override
	public boolean hasFindAllMethod() {
		return invoker.hasFindAllMethod();
	}

	@Override
	public <T> T invokeSave(T object) {
		return invoker.invokeSave(object);
	}

	@Override
	public <T> T invokeFindOne(Serializable id) {
		return invoker.invokeFindOne(id);
	}

	@Override
	public Iterable<Object> invokeFindAll(Pageable pageable) {
		return invoker.invokeFindAll(pageable);
	}

	@Override
	public Iterable<Object> invokeFindAll(Sort sort) {
		return invoker.invokeFindAll(sort);
	}

	@Override
	public void invokeDelete(Serializable id) {
		invoker.invokeDelete(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object invokeQueryMethod(Method method, Map<String, String[]> parameters, Pageable pageable, Sort sort) {
		return invoker.invokeQueryMethod(method, parameters, pageable, sort);
	}

	@Override
	public Object invokeQueryMethod(Method method, MultiValueMap<String, ? extends Object> parameters,
			Pageable pageable, Sort sort) {
		return invoker.invokeQueryMethod(method, parameters, pageable, sort);
	}

}
