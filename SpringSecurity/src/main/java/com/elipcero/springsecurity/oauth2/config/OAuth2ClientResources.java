package com.elipcero.springsecurity.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
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
	
	private OAuth2ProtectedResourceDetails client = new AuthorizationCodeResourceDetails();
	private ResourceServerProperties resource = new ResourceServerProperties();

	public OAuth2ProtectedResourceDetails getClient() {
		return client;
	}

	public ResourceServerProperties getResource() {
		return resource;
	}
}