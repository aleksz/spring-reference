package com.gmail.at.zhuikov.aleksandr.root.domain;


public enum GrantedAuthority implements
		org.springframework.security.core.GrantedAuthority {

	USER, CLIENT, WORKER, ADMIN;
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		return toString();
	}
}
