package com.gmail.at.zhuikov.aleksandr.root.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}