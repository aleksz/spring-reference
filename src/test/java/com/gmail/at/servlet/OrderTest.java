package com.gmail.at.servlet;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderTest {

	@Test
	public void test() {
		Order order = new Order();
		Item item1 = new Item(order);
		Item item2 = new Item(order);
	}

}
