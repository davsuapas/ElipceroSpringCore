package com.elipcero.springdata.integrationtest.configuration.mongo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.elipcero.springdata.repositories.mongo.MongoExtendedFactoryBean;

/**
 * @author dsuarez
 * 
 * Configure base packages for mongo repositories
 *
 */

@Configuration
@SpringBootApplication
@EnableMongoRepositories(
		basePackages = "com.elipcero.springdata.integrationtest.repositories.mongo",
		repositoryFactoryBeanClass = MongoExtendedFactoryBean.class
)
public class MongoDataConfiguration {
}
