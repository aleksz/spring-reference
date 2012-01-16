package com.gmail.at.zhuikov.aleksandr.root.domain;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class DefaultItemTest {

	@Test
	public void test() {
		Order order = new Order("x");
		DefaultItem item = new DefaultItem(order);
		assertEquals("", item.getProduct());
		assertEquals(0d, item.getPrice());
		assertEquals(order, item.getOrder());
	}

}
