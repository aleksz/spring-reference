package com.gmail.at.zhuikov.aleksandr.root.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
}