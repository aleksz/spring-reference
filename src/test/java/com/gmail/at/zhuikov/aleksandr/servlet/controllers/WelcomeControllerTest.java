package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class WelcomeControllerTest {

	private WelcomeController controller = new WelcomeController();
	
	@Test
	public void test() {
		assertEquals("redirect:/orders", controller.redirectToOrders());
	}

}
