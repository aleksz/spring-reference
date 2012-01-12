package com.gmail.at.zhuikov.aleksandr.it;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.PageFactory.initElements;

import org.junit.Test;

import com.gmail.at.zhuikov.aleksandr.it.page.AddItemPage;
import com.gmail.at.zhuikov.aleksandr.it.page.AddOrderPage;
import com.gmail.at.zhuikov.aleksandr.it.page.EditOrderPage;
import com.gmail.at.zhuikov.aleksandr.it.page.OrdersPage;

public class AllIT extends AbstractWebDriverTest {

	public AllIT() {
		super("orders");
	}

	@Test
	public void theTest() {
		
		String customerName = getUniqueCharString();
		
		AddOrderPage addOrderPage = initElements(driver, OrdersPage.class)
				.clickAddNewOrder()
				.clickSaveButtonAndExpectErrors();
		
		assertTrue(addOrderPage.hasCustomerErrors());
		assertTrue(addOrderPage.hasEmailErrors());
		
		EditOrderPage editOrderPage = addOrderPage
				.setCustomer(customerName)
				.setEmail("mail@example.com")
				.clickSaveButton()
				.clickOrder(customerName);
		
		assertEquals(customerName, editOrderPage.getCustomer());
		assertEquals("mail@example.com", editOrderPage.getEmail());
		
		editOrderPage = editOrderPage
				.setEmail("wrong mail")
				.clickSaveButtonAndExpectErrors();
		
		assertTrue(editOrderPage.hasEmailErrors());
		
		editOrderPage = editOrderPage
				.setEmail("changed@example.com")
				.clickSaveButton()
				.clickOrder(customerName);
		
		assertEquals("changed@example.com", editOrderPage.getEmail());
		
		AddItemPage addItemPage = editOrderPage
				.clickAddItemLink()
				.setPrice("-1")
				.clickSaveButtonAndExpectErrors();
		
		assertTrue(addItemPage.hasProductErrors());
		assertTrue(addItemPage.hasPriceErrors());
		assertTrue(addItemPage.hasQuantityErrors());
		
		addItemPage
				.setProduct("test product")
				.setPrice("2555.12")
				.setQuantity("344234")
				.clickSaveButton();
		
		assertTrue(editOrderPage.hasItem("test product 344234 x 2555.12$"));
		
		OrdersPage ordersPage = editOrderPage.clickDeleteButton();
		
		assertFalse(ordersPage.hasOrder(customerName));
	}
}
