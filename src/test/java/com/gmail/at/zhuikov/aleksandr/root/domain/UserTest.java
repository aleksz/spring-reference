package com.gmail.at.zhuikov.aleksandr.root.domain;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {

	@Test
	public void alwaysEnabled() {
		assertTrue(new User("c").isEnabled());
	}
	
	@Test
	public void credentialsAreNeverExpired() {
		assertTrue(new User("c").isCredentialsNonExpired());
	}
	
	@Test
	public void accountIsNeverLocked() {
		assertTrue(new User("c").isAccountNonLocked());
	}
	
	@Test
	public void accountIsNeverExpired() {
		assertTrue(new User("c").isAccountNonExpired());
	}
	
	@Test
	public void passwordIsAlwaysNull() {
		assertNull(new User("c").getPassword());
	}
	
	@Test
	public void userNotEqualToNull() {
		assertFalse(new User("x").equals(null));
	}

	@Test
	public void userEqualsToItself() {
		User user = new User("x");
		assertTrue(user.equals(user));
	}
	
	@Test
	public void userNotEqualToOtherClass() {
		assertFalse(new User("x").equals("x"));
	}
	
	@Test
	public void usersNotEqualIfUsernameIsDifferent() {
		assertFalse(new User("a").equals(new User("b")));
	}
}
