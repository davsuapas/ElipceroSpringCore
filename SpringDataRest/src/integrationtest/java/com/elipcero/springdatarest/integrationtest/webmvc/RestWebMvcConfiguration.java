package com.elipcero.springdatarest.integrationtest.webmvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.elipcero.springdata.repositories.mongo.MongoExtendedFactoryBean;
import com.elipcero.springdatarest.webmvc.RepositoryExtensionController;

@Configuration
@SpringBootApplication(scanBasePackageClasses=RepositoryExtensionController.class)
@EnableMongoRepositories(
		basePackages = "com.elipcero.springdatarest.integrationtest.webmvc",
		repositoryFactoryBeanClass = MongoExtendedFactoryBean.class
)
public class RestWebMvcConfiguration {
}

