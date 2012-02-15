package com.gmail.at.zhuikov.aleksandr.root.domain;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.EnumType.STRING;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "app_user")
@Access(FIELD)
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	
	private Locale locale;

	@ElementCollection(targetClass = GrantedAuthority.class)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "username"))
	@Column(name = "authority_id", nullable = false)
	@Enumerated(STRING)
	private Set<org.springframework.security.core.GrantedAuthority> authorities = 
			new HashSet<org.springframework.security.core.GrantedAuthority>();

	private User() {
	}
	
	public User(String username) {
		this();
		this.username = username;
	}
	
    public void setLocale(Locale locale) {
		this.locale = locale;
	}
    
    public Locale getLocale() {
		return locale;
	}
    
    public String getUsername() {
    	return username;
    }
    
    @Override
    public Collection<org.springframework.security.core.GrantedAuthority> getAuthorities() {
    	return authorities;
    }

	@Override
	public String getPassword() {
		return null;
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
	public int hashCode() {
		return new HashCodeBuilder().append(username).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		
		User other = (User) obj;
		
		return new EqualsBuilder().append(username, other.getUsername()).isEquals();
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this).append(username).toString();
	}
}
