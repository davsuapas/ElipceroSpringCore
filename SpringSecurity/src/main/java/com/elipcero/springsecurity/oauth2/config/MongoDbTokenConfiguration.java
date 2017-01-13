package com.elipcero.springsecurity.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.elipcero.springsecurity.oauth2.MongoDBTokenStore;
import com.elipcero.springsecurity.oauth2.repository.OAuth2AccessTokenRepository;
import com.elipcero.springsecurity.oauth2.repository.OAuth2RefreshTokenRepository;

/**
 * Configure repositories and make MongoDBTokenStore. @see MongoDBTokenStore
 * 
 * @author David Suárez Pascual
 */
@Configuration
@EnableMongoRepositories(basePackages="com.elipcero.springsecurity.oauth2.repository")
public class MongoDbTokenConfiguration {

    @Autowired
    private OAuth2AccessTokenRepository oAuth2AccessTokenRepository;

    @Autowired
    private OAuth2RefreshTokenRepository oAuth2RefreshTokenRepository;
    
    /**
     * Return the bean to manage token repository using mongodb
     *
     * @return the token store
    */
    @Bean
    public TokenStore mongoTokenStore() {
        return new MongoDBTokenStore(oAuth2AccessTokenRepository, oAuth2RefreshTokenRepository);
    }	
}
