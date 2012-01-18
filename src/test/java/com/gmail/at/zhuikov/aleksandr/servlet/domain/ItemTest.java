package com.gmail.at.zhuikov.aleksandr.servlet.domain;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class ItemTest {

	private static Validator validator;
	private Order order;

	@BeforeClass
	public static void setUp() {
		validator = buildDefaultValidatorFactory().getValidator();
	}

	@Before
	public void prepareOrder() {
		order = new Order("thecustomer");
		order.setEmail("a@a.a");
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		assertTrue(constraintViolations.toString(), constraintViolations.isEmpty());
	}

	@Test
	public void noValidationErrorsWhenItemIsValid() {
		Item item = new Item(order, "x", 0);
		item.setQuantity(1);
		Set<ConstraintViolation<Item>> violations = validator.validate(item);
		assertTrue(violations.isEmpty());
	}
	
	@Test
	public void duplicatedProductInOrderIsNotAllowed() throws Exception {
		Item item1 = new Item(order, "x", 0);
		item1.setQuantity(1);
		
		Item item2 = new Item(order, "", 0);
		item2.setQuantity(1);
		
		ReflectionTestUtils.setField(item2, "product", "x");
		
		new Item(order, "xx", 0);
		
		Set<ConstraintViolation<Item>> violations = validator.validate(item2);
		assertEquals(2, violations.size());
	}
	
	@Test
	public void nullProductIsNotAllowed() {
		Item item = new Item(order, null, 0);
		Set<ConstraintViolation<Item>> violations = validator.validateProperty(item, "product");
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void emptyStringProductIsNotAllowed() {
		Item item = new Item(order, " ", 0);
		Set<ConstraintViolation<Item>> violations = validator.validateProperty(item, "product");
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void negativePriceIsNotAllowed() {
		Item item = new Item(order, "x", -1);
		Set<ConstraintViolation<Item>> violations = validator.validateProperty(item, "price");
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void quantityLessThanOneIsNotAllowed() {
		Item item = new Item(order, "x", 0);
		item.setQuantity(0);
		Set<ConstraintViolation<Item>> violations = validator.validateProperty(item, "quantity");
		assertFalse(violations.isEmpty());
	}
	
	@Test
	public void equalsWhenOrderProductAndPricedAreTheSame() {
		Item item1 = new Item(order, "x", 1);
		item1.setQuantity(1);
		Item item2 = new Item(order, "x", 1);
		item1.setQuantity(2);
		assertEquals(item1, item2);
	}
	
	@Test
	public void notEqualWhenOrderIsDifferent() {
		Item item1 = new Item(order, "x", 1);
		Item item2 = new Item(new Order("anotherClient"), "x", 1);
		assertFalse(item1.equals(item2));
	}
	
	@Test
	public void notEqualWhenProductIsDifferent() {
		Item item1 = new Item(order, "x", 1);
		Item item2 = new Item(order, "xx", 1);
		assertFalse(item1.equals(item2));
	}
	
	@Test
	public void notEqualWhenPriceIsDifferent() {
		Item item1 = new Item(order, "x", 1);
		Item item2 = new Item(order, "x", 2);
		assertFalse(item1.equals(item2));
	}
	
	@Test
	public void notEqualToNull() {
		Item item = new Item(order, "x", 1);
		assertFalse(item.equals(null));
	}
	
	@Test
	public void doesNotGiveClassCastExceptionWhenComparingWithOtherClass() {
		Item item = new Item(order, "x", 1);
		assertFalse(item.equals(order));
	}
	
	@Test
	public void equalsWhenComparingToItself() {
		Item item = new Item(order, "x", 1);
		assertEquals(item, item);
	}
	
	@Test
	public void itemsWithSameProductAndPriceAndOrderHaveSameHashCodel() {
		Item item1 = new Item(order, "a", 5);
		Item item2 = new Item(order, "a", 5);
		assertEquals(item1.hashCode(), item2.hashCode());
	}
}
