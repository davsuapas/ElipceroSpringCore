package com.elipcero.springsecurity.web;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Mongo user details entity
 *
 * @author dav.sua.pas@gmail.com
 */
@SuppressWarnings("serial")
@Document(collection = "user_details")
public class MongoDbUserDetails implements UserDetails {

	@Indexed
	private String userName;
	
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;	
    private boolean credentialsNonExpired;
    private boolean enabled;
    
    private List<GrantedAuthority> authorities;
    
    public MongoDbUserDetails() {
    }

    public MongoDbUserDetails(String userName, String password) {
    	this(userName, password, true, true, true, true, null);
    }

    public MongoDbUserDetails(String userName, String password,
    		List<? extends GrantedAuthority> authorities) {
    	
    	this(userName, password, true, true, true, true, authorities);
    }
    
    public MongoDbUserDetails(String userName, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, List<? extends GrantedAuthority> authorities) {

		if (userName == null || userName.isEmpty() || password == null) {
			throw new IllegalArgumentException(
					"Cannot pass null or empty values to constructor");
		}

		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
        if (authorities != null) {
		    this.authorities =  Collections.unmodifiableList(authorities);
        }
	}
    @Id
    private String id;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
