package com.gmail.at.servlet;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.gmail.at.zhuikov.aleksandr.servlet.domain.Item;
import com.gmail.at.zhuikov.aleksandr.servlet.domain.Order;

public class ItemTest {

	@Test
	public void itemsWithSameProductAndPriceAndOrderAreEqual() {
		Order order = new Order("c");
		Item item1 = new Item(order, "a", 5);
		Item item2 = new Item(order, "a", 5);
		assertEquals(item1, item2);
	}
	
	@Test
	public void itemsWithSameProductAndPriceAndOrderHaveSameHashCodel() {
		Order order = new Order("c");
		Item item1 = new Item(order, "a", 5);
		Item item2 = new Item(order, "a", 5);
		assertEquals(item1.hashCode(), item2.hashCode());
	}
}
