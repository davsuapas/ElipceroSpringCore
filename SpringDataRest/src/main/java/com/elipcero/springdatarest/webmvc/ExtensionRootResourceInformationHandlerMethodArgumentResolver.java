package com.elipcero.springdatarest.webmvc;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.repository.support.RepositoryInvoker;
import org.springframework.data.repository.support.RepositoryInvokerFactory;
import org.springframework.data.rest.webmvc.config.ResourceMetadataHandlerMethodArgumentResolver;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;

import com.elipcero.springdata.repositories.base.ExtensionRepository;
import com.elipcero.springdata.repositories.base.RepositoryExtensionInvoker;
import com.elipcero.springdata.repositories.mongo.MongoExtensionRepository;
import com.elipcero.springdata.repositories.mongo.MongoRepositoryExtensionInvoker;

/**
 * Override postProcess to allow a new invoker for repository extension, getting more features
 * 
 * @author dav.sua.pas@gmail.com
 */
public class ExtensionRootResourceInformationHandlerMethodArgumentResolver extends RootResourceInformationHandlerMethodArgumentResolver {
	
	private final Repositories repositories;

	/**
	 * Instantiates a new extension root resource information handler method argument resolver.
	 *
	 * @param repositories the repositories
	 * @param invokerFactory the invoker factory
	 * @param resourceMetadataResolver the resource metadata resolver
	 */
	public ExtensionRootResourceInformationHandlerMethodArgumentResolver(
			Repositories repositories,
			RepositoryInvokerFactory invokerFactory,
			ResourceMetadataHandlerMethodArgumentResolver resourceMetadataResolver) {
		
		super(repositories, invokerFactory, resourceMetadataResolver);
		
		this.repositories = repositories;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver#postProcess(org.springframework.core.MethodParameter, org.springframework.data.repository.support.RepositoryInvoker, java.lang.Class, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected RepositoryInvoker postProcess(MethodParameter parameter, RepositoryInvoker invoker, Class<?> domainType, Map<String, String[]> parameters) {
		Object repository = repositories.getRepositoryFor(domainType);
		
		if (repository instanceof MongoExtensionRepository<?, ?> )
			return new MongoRepositoryExtensionInvoker(invoker, (MongoExtensionRepository<Object, ?>)repository);
		else if (repository instanceof ExtensionRepository<?> )
			return new RepositoryExtensionInvoker(invoker, (ExtensionRepository<Object>)repository);
		else
			return invoker; // By default
	}	
}
