package com.elipcero.springsecurity.integrationtest.web;

import com.elipcero.springsecurity.web.MongoDbUserDetails;
import com.elipcero.springsecurity.web.MongoDbUserDetailsRepository;
import com.elipcero.springsecurity.web.MongoDbUserDetailsServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MongoDbUserUserDetailsConfiguration.class)
public class MongoUserDetailsServiceIntegrationTest {
	
	private static final String CONST_PASSWORD = "password";
	private static final String CONST_USER_NAME = "userName";

	@Autowired
	private MongoDbUserDetailsServices service;

	@Autowired
	private MongoDbUserDetailsRepository repository;
	
	@Test
	public void userDetails_loadUserByUsername_shouldReturnUserDetails() {
		
		GrantedAuthority authority = new SimpleGrantedAuthority("role");
		
		MongoDbUserDetails userDetailsSaved = 
				new MongoDbUserDetails(
					CONST_USER_NAME,
					CONST_PASSWORD,
					Collections.singletonList(authority));
		
		this.repository.save(userDetailsSaved);
		
		UserDetails userDetails = this.service.loadUserByUsername(CONST_USER_NAME);
		
		assertThat(userDetails.getUsername()).isEqualTo(CONST_USER_NAME);
		assertThat(userDetails.getPassword()).isEqualTo(CONST_PASSWORD);
		assertThat(userDetails.isAccountNonExpired()).isTrue();
		assertThat(userDetails.isAccountNonLocked()).isTrue();
		assertThat(userDetails.isCredentialsNonExpired()).isTrue();
		assertThat(userDetails.isEnabled()).isTrue();
		assertThat(userDetails.getAuthorities().stream().findFirst().get()).isEqualTo(authority);
		
		this.repository.delete(userDetailsSaved);
	}
}
