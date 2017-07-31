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

public class ExtensionRootResourceInformationHandlerMethodArgumentResolver extends RootResourceInformationHandlerMethodArgumentResolver {
	
	private final Repositories repositories;

	public ExtensionRootResourceInformationHandlerMethodArgumentResolver(
			Repositories repositories,
			RepositoryInvokerFactory invokerFactory,
			ResourceMetadataHandlerMethodArgumentResolver resourceMetadataResolver) {
		
		super(repositories, invokerFactory, resourceMetadataResolver);
		
		this.repositories = repositories;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected RepositoryInvoker postProcess(MethodParameter parameter, RepositoryInvoker invoker, Class<?> domainType, Map<String, String[]> parameters) {
		Object repository = repositories.getRepositoryFor(domainType);
		return new RepositoryExtensionInvoker(invoker, (ExtensionRepository<Object>)repository);
	}	
}
