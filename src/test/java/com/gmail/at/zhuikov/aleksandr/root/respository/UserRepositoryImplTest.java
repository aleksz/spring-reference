package com.gmail.at.zhuikov.aleksandr.root.respository;

import static junit.framework.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;

public class UserRepositoryImplTest {

	private UserRepository userRepository = new UserRepositoryImpl();
	
	@Test
	public void findsSavedUser() {
		User user = new User("username", Collections.<GrantedAuthority>emptyList());
		userRepository.save(user);
		assertEquals(user, userRepository.find("username"));
	}

}
