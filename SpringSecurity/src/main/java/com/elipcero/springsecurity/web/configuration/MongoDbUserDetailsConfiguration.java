package com.elipcero.springsecurity.web.configuration;

import com.elipcero.springsecurity.web.MongoDbUserDetailsRepository;
import com.elipcero.springsecurity.web.MongoDbUserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * Configure mongo user details service
 *
 * @author dav.sua.pas@gamail.com
 */
@Configuration
@EnableMongoRepositories(basePackages="com.elipcero.springsecurity.web")
public class MongoDbUserDetailsConfiguration {
	
	@Autowired
	private MongoDbUserDetailsRepository repository;
	
	@Bean
	public UserDetailsService mongoUserDetailsService() {
        Assert.notNull(repository, "MongoDbUserDetailsConfiguration: Mongo user repository cannot be null");
		return new MongoDbUserDetailsServices(repository);
	}
}
