package com.elipcero.springdatarest.integrationtest.webmvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.elipcero.springdata.repositories.mongo.MongoExtendedFactoryBean;
import com.elipcero.springdatarest.webmvc.EnabledRepositoryExtensionRestMvc;

@Configuration
@SpringBootApplication()
@EnableMongoRepositories(
		basePackages = "com.elipcero.springdatarest.integrationtest.webmvc",
		repositoryFactoryBeanClass = MongoExtendedFactoryBean.class
)
@EnabledRepositoryExtensionRestMvc
public class RestWebMvcConfiguration {
}

