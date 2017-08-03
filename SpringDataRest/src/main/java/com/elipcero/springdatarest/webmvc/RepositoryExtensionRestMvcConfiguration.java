package com.elipcero.springdatarest.webmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.querydsl.QueryDslUtils;
import org.springframework.data.rest.webmvc.config.PersistentEntityResourceHandlerMethodArgumentResolver;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.rest.webmvc.config.RootResourceInformationHandlerMethodArgumentResolver;
import org.springframework.data.rest.webmvc.json.DomainObjectReader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;


/**
 * Override repoRequestArgumentResolver for getting more features of others repositories.
 *
 * @author dav.sua.pas@gmail.com
 */
@Configuration
@ComponentScan(basePackageClasses = RepositoryExtensionController.class)
public class RepositoryExtensionRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	// Optional Deserializer
	@Bean
	public RepositoryRestConfigurerAdapter configExtension() {
		return new RepositoryRestConfigurerAdapter() {
			@Override
			public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
				objectMapper.registerModule(new Jdk8Module());
			}
		};
	}
	
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
    
    /* (non-Javadoc)
     * @see org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration#persistentEntityArgumentResolver()
     */
    @Override
	@Bean
	public PersistentEntityResourceHandlerMethodArgumentResolver persistentEntityArgumentResolver() {

		return new ExtensionPersistentEntityResourceHandlerMethodArgumentResolver(defaultMessageConverters(),
				repoRequestArgumentResolver(), backendIdHandlerMethodArgumentResolver(),
				new DomainObjectReader(persistentEntities(), associationLinks()));
	}
}
