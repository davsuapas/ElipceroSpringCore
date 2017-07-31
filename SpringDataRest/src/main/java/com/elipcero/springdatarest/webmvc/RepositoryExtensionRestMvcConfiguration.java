package com.elipcero.springdatarest.webmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;

@Configuration
public class RepositoryExtensionRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    @Bean
    public RootResourceInformationHandlerMethodArgumentResolver repoRequestArgumentResolver() {

        if (QueryDslUtils.QUERY_DSL_PRESENT) {
        	return super.repoRequestArgumentResolver();
        }

        return new ExtensionRootResourceInformationHandlerMethodArgumentResolver(
        		repositories(),
                repositoryInvokerFactory(defaultConversionService()),
                resourceMetadataHandlerMethodArgumentResolver());
    }
}
