package com.elipcero.springdatarest.webmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;


/**
 * Override repoRequestArgumentResolver for getting more features of others repositories
 * 
 * @author dav.sua.pas@gmail.com
 */
@Configuration
@ComponentScan(basePackageClasses = RepositoryExtensionController.class)
public class RepositoryExtensionRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    /* (non-Javadoc)
     * @see org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration#repoRequestArgumentResolver()
     */
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
