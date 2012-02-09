package com.gmail.at.zhuikov.aleksandr.root.respository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final Map<String, User> registeredUsers = new HashMap<String, User>();
	
	@Override
	public User find(String username) {
		return registeredUsers.get(username);
	}
	
	@Override
	public void save(User user) {
		registeredUsers.put(user.getUsername(), user);
	}
}
