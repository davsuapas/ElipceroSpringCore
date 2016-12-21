package com.elipcero.springsecurity.oauth2.config.unittest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.elipcero.springsecurity.oauth2.config.MongoDbTokenConfiguration;

public class MongoDbTokenConfigurationUnitTest {

	@Test
	public void MongoDbConfiguration_GetMongoTokenStore_ShouldReturnNotNull() {
		
		MongoDbTokenConfiguration configuration = new MongoDbTokenConfiguration();
		
		assertThat(configuration.mongoTokenStore()).isNotNull();
	}
}
