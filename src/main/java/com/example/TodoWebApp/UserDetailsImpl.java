package com.example.TodoWebApp;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String username;
    private String usernamejp;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String usernamejp, String password, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.usernamejp = usernamejp;
        this.password = password;
        this.authorities = authorities;
    }

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    public String getUsernamejp() {
        return usernamejp;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
         return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
	public String getUsername() {
		return username;
	}
}
