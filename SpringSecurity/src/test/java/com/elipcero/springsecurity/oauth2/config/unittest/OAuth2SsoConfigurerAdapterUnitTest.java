package com.elipcero.springsecurity.oauth2.config.unittest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import javax.servlet.Filter;

import org.junit.Test;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;

import com.elipcero.springsecurity.oauth2.config.OAuth2ClientResources;
import com.elipcero.springsecurity.oauth2.config.OAuth2SsoConfigurerAdapter;

public class OAuth2SsoConfigurerAdapterUnitTest {
	
	@Test
	public void OAuth2SsoConfigurerAdapter_oauth2ClientFilterRegistration_ShouldReturnFilterRegistrationBean() {
		
		OAuth2SsoConfigurerAdapterTest oAuth2SsoConfigurerAdapterTest = new OAuth2SsoConfigurerAdapterTest();
		FilterRegistrationBean filter = oAuth2SsoConfigurerAdapterTest.oauth2ClientFilterRegistration(new OAuth2ClientContextFilter());

		assertThat(filter.getFilter()).isNotNull();
		assertThat(filter.getOrder()).isNegative();
	}
	
	@Test
	public void OAuth2SsoConfigurerAdapter_ssoFilter_ShouldReturnFilter() {
		
		OAuth2SsoConfigurerAdapterTest oAuth2SsoConfigurerAdapterTest = new OAuth2SsoConfigurerAdapterTest();
		Filter filter = oAuth2SsoConfigurerAdapterTest.ssoFilter();

		assertThat(filter).isNotNull();
	}
	
	@Test
	public void OAuth2SsoConfigurerAdapter_ssoFilterWithClientResource_ShouldReturnFilter() {
		
		OAuth2SsoConfigurerAdapterTest oAuth2SsoConfigurerAdapterTest = new OAuth2SsoConfigurerAdapterTest();
		OAuth2ClientAuthenticationProcessingFilter filter = (OAuth2ClientAuthenticationProcessingFilter)oAuth2SsoConfigurerAdapterTest.ssoFilterWithClientResource();

		assertThat(filter.restTemplate).isNotNull();
	}
	
	private class OAuth2SsoConfigurerAdapterTest extends OAuth2SsoConfigurerAdapter {
		
		public Filter ssoFilter() {
			return this.ssoFilter(new ArrayList<>());
		}
		
		public Filter ssoFilterWithClientResource() {
			return this.ssoFilter(new OAuth2ClientResources(), "path");
		}
	}
}
