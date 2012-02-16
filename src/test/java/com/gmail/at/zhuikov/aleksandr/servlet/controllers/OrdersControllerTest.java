package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.DESC;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;

public class OrdersControllerTest {

	@Mock
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrdersController controller;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void loadAllOrders() {
		Page<Order> orders = new PageImpl<Order>(asList(new Order("a"), new Order("b")));
		when(orderRepository.findAll(new PageRequest(0, 20, DESC, "date"))).thenReturn(orders);
		Page<Order> result = controller.loadAllOrders(0);
		assertEquals(orders, result);
	}

	@Test
	public void list() {
		assertEquals("orders", controller.list(new PageImpl<Order>(asList(new Order("x")))));
	}
}
