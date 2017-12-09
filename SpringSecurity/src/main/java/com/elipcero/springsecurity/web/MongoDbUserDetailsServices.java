package com.elipcero.springsecurity.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Mongo user details service. Implements UserDetailsService
 *
 * @author dav.sua.pas@gmail.com
 */
@RequiredArgsConstructor
public class MongoDbUserDetailsServices implements UserDetailsService {
	
	@NonNull private MongoDbUserDetailsRepository repository; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		MongoDbUserDetails userDetails = this.repository.findByUserName(username);
		
		if (userDetails == null) {
			throw new UsernameNotFoundException(username);
		}
		else {
			return userDetails;
		}
	}
}
