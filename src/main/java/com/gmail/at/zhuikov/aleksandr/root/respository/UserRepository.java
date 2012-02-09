package com.gmail.at.zhuikov.aleksandr.root.respository;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;

public interface UserRepository {

	User find(String username);

	void save(User user);

}