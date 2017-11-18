package com.elipcero.springsecurity.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * Setting into OAuth2ClientResources oauth2 client information
 * using {@link ConfigurationProperties}
 * 
 * Example:
 * 
 * 	@Bean
 *	@ConfigurationProperties("github")
 *	OAuth2ClientResources github() {
 *		return new OAuth2ClientResources();
 *	}
 * 
 * @author David Suárez Pascual
 */
public class OAuth2ClientResources {
	
	@NestedConfigurationProperty
	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
	
	@NestedConfigurationProperty
	private ResourceServerProperties resource = new ResourceServerProperties();

	public AuthorizationCodeResourceDetails getClient() {
		return client;
	}

	public ResourceServerProperties getResource() {
		return resource;
	}
}