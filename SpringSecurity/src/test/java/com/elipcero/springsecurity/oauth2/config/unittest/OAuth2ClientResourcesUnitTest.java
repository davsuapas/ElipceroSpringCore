package com.elipcero.springsecurity.oauth2.config.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import com.elipcero.springsecurity.oauth2.config.OAuth2ClientResources;

public class OAuth2ClientResourcesUnitTest {

	@Test
	public void OAuth2ClientResources_GetClient_ShouldReturnNotNull() {
		
		OAuth2ClientResources oAuth2ClientResources = new OAuth2ClientResources();
		
		assertThat(oAuth2ClientResources.getClient()).isNotNull();
	}
	
	@Test
	public void OAuth2ClientResources_GetResources_ShouldReturnNotNull() {
		
		OAuth2ClientResources oAuth2ClientResources =new OAuth2ClientResources();
		
		assertThat(oAuth2ClientResources.getResource()).isNotNull();
	}
}
