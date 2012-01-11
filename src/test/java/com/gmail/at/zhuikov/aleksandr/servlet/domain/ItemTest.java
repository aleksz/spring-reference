package com.gmail.at.zhuikov.aleksandr.servlet.domain;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ItemTest {

	private static Validator validator;
	private Order order;

	@BeforeClass
	public static void setUp() {
		validator = buildDefaultValidatorFactory().getValidator();
	}

	@Before
	public void prepareOrder() {
		order = new Order("c");
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
		
		Field productField = item2.getClass().getDeclaredField("product");
		productField.setAccessible(true);
		productField.set(item2, "x");
		
		Set<ConstraintViolation<Item>> violations = validator.validate(item2);
		assertFalse(violations.isEmpty());
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
}
