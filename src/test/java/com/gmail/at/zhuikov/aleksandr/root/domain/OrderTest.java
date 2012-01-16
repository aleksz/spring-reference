package com.gmail.at.zhuikov.aleksandr.root.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class OrderTest {

	@Test
	public void orderNotEqualToNull() {
		assertFalse(new Order("x").equals(null));
	}

	@Test
	public void orderEqualsToItself() {
		Order order = new Order("x");
		assertTrue(order.equals(order));
	}
	
	@Test
	public void orderNotEqualToOtherClass() {
		assertFalse(new Order("x").equals("x"));
	}
	
	@Test
	public void ordersNotEqualIfCustomerIsDifferent() {
		assertFalse(new Order("a").equals(new Order("b")));
	}
	
	@Test
	public void ordersNotEqualIfDateIsDifferent() {
		Order order1 = new Order("a");
		Order order2 = new Order("a");
		ReflectionTestUtils.setField(order2, "date", new Date(1));
		assertFalse(order1.equals(order2));
	}
}
