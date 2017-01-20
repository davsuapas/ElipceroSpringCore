package com.elipcero.springsecurity.oauth2.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow use MongoDBTokenStore. @see MongoDBTokenStore
 * 
 * <p>
 * Example:
 * 
 * @Configuration
 * @EnabledMongoDbToken
 * @EnableAuthorizationServer
 * public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
 *	
 *	@Autowired
 *	private AuthenticationManager authenticationManager;
 *
 *	// Inject MongoDbTokenStore by @EnabledMongoDbToken 
 *	@Autowired
 *	private TokenStore tokenStore;
 *
 * }
 * <p>
 * 
 * @author David Suárez Pascual
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MongoDbTokenConfiguration.class)
public @interface EnabledMongoDbToken {
}
