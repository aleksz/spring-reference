package com.gmail.at.zhuikov.aleksandr.root.respository;

import java.util.List;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public interface OrderRepository {

	List<Order> getAll();
	
	void save(Order order);
	
	void update(Order order);
	
	Order load(long id);
	
	void delete(Order order);
}