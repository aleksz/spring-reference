package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	public void test() {
		List<Order> orders = asList(new Order("a"), new Order("b"));
		when(orderRepository.findAll()).thenReturn(orders);
		List<Order> result = controller.loadAllOrders();
		assertEquals(orders, result);
	}

	@Test
	public void list() {
		assertEquals("orders", controller.list(asList(new Order("x"))));
	}
}
