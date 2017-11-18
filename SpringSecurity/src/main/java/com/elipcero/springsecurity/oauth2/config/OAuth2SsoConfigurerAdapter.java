package com.elipcero.springsecurity.oauth2.config;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;

/**
 * Configure filters OAuth2 client in a web application that uses Spring Security and wants 
 * to use the Authorization Code Grant from one or more OAuth2 Authorization servers
 * The filter is configured in priority order. Order => -100 
 * 
 * Example:
 * 
 * <p>
 * @Configuration
 * public class WebSecurityConfiguration extends OAuth2SsoConfigurerAdapter {
 *	
 *	@Override
 *	protected void configure(HttpSecurity http) throws Exception {
 *		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll()
 *				.anyRequest()
 *				.authenticated().and().exceptionHandling()
 *				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()
 *				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
 *	}
 *
 *	private Filter ssoFilter() {
 *		return ssoFilter(
 *				Arrays.asList(
 *						ssoFilter(github(), "/login/google")
 *				)
 *		);
 *	}
 *	
 *	@Bean
 *	@ConfigurationProperties("google")
 *	OAuth2ClientResources github() {
 *		return new OAuth2ClientResources();
 *	}
 * }
 * <p>
 * 
 * @author David Suárez Pascual
 */

@EnableOAuth2Client  
public abstract class OAuth2SsoConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	/**
	 * Oauth2 client filter registration.
	 *
	 * @param filter the filter
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}	

	/**
	 * Setting sso filter.
	 *
	 * @param filters the filters
	 * @return the composite filter
	 */
	protected Filter ssoFilter(List<Filter> filters) {
		CompositeFilter filter = new CompositeFilter();
		filter.setFilters(filters);
		return filter;
	}

	/**
	 * From oauth2 client resources and path, setting oauth2 rest template
	 *
	 * @param client the oauth2 client resources
	 * @param path the path
	 * @return the filter with rest templates
	 */
	protected Filter ssoFilter(OAuth2ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		return filter;
	}	
}
