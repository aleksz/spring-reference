package com.gmail.at.zhuikov.aleksandr.root.domain;

import java.util.Collection;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {
	
	private static final long serialVersionUID = 1L;

	private Locale locale;
    
    public User(String username, Collection<GrantedAuthority> authorities) {
        super(username, "unused", authorities);
    }

    public void setLocale(Locale locale) {
		this.locale = locale;
	}
    
    public Locale getLocale() {
		return locale;
	}
}
