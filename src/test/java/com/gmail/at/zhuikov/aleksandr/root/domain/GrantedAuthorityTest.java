package com.gmail.at.zhuikov.aleksandr.root.domain;

import static com.gmail.at.zhuikov.aleksandr.root.domain.GrantedAuthority.ADMIN;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityTest {

	@Test
	public void usesEnumStringValueForAuthority() {
		GrantedAuthority authority = ADMIN;
		assertEquals("ADMIN", authority.getAuthority());
	}

	@Test
	public void stupidEnumCoverageTest() {
		com.gmail.at.zhuikov.aleksandr.root.domain.GrantedAuthority.valueOf(ADMIN.toString());
	}
}
