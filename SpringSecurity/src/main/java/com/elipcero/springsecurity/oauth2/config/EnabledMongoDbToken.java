package com.elipcero.springsecurity.oauth2.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow use MongoDBTokenStore. @see MongoDBTokenStore
 * 
 * Example:
 * 
 * @Bean 
 * public AuthorizationServerTokenServices tokenServices() {
 *	    DefaultTokenServices tokenServices = new DefaultTokenServices();
 *	    
 *	    tokenServices.setTokenStore(mongoTokenStore());
 *	    return tokenServices;
 *	}
 * 
 * @author David Suárez Pascual
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MongoDbTokenConfiguration.class)
public @interface EnabledMongoDbToken {
}
