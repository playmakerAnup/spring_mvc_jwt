package com.example.demo.jwt.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Bean class for handling JWT authenticated users<br>
 * <h4>Created On: 18th-Jan-2019</h4><br>
 * 
 * @author Anup Nair
 */
public class AuthenticatedUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String username;
    private  String token;
    private Collection<? extends GrantedAuthority> authorities;
    
    public AuthenticatedUser() {
		// TODO Auto-generated constructor stub
	}
    
    public AuthenticatedUser(String token, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.authorities = authorities;
    }

	@Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

}