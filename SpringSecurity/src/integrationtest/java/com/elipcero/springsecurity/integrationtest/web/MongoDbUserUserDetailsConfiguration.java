package com.elipcero.springsecurity.integrationtest.web;

import com.elipcero.springsecurity.web.configuration.EnabledMongoDbUserDetails;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication()
@EnabledMongoDbUserDetails
public class MongoDbUserUserDetailsConfiguration {
}
