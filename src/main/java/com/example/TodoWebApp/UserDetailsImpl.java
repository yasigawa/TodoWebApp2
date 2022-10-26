package com.example.TodoWebApp;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userid;
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(String userid, String username, String password, Collection<GrantedAuthority> authorities) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

//	@Override
//    public String getUserid() {
//		return userid;
//	}
//
//	public void setUserid(String userid) {
//		this.userid = userid;
//	}
//
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
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

	public String getUserid() {
		return userid;
	}

}
