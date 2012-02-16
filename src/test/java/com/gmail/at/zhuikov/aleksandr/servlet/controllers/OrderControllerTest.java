package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;

public class OrderControllerTest {

	@Mock 
	private OrderRepository orderRepository;
	
	@InjectMocks
	private OrderController controller;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void delete() {
		Order order = new Order("x");
		String view = controller.delete(order);
		assertEquals("redirect:/orders", view);
		verify(orderRepository).delete(order);
	}
	
	@Test
	public void read() {
		assertEquals("editOrder", controller.read(new Order("x")));
	}
	
	@Test
	public void prepareOrder() throws OrderNotFoundException {
		Order expected = new Order("x");
		when(orderRepository.findOne(2L)).thenReturn(expected);
		Order order = controller.prepareOrder(2L);
		assertEquals(expected, order);
	}
	
	@Test(expected = OrderNotFoundException.class)
	public void prepareOrderThrowsExceptionWhenOrderNotFound() throws OrderNotFoundException {
		when(orderRepository.findOne(2L)).thenReturn(null);
		controller.prepareOrder(2L);
	}

	@Test
	public void create() throws ModelAndViewDefiningException {
		Order order = new Order("x");
		BindingResult errors = new BindException(order, "order");
		Model model = new ExtendedModelMap();
		
		String view = controller.update(order, errors, model);
		verify(orderRepository).save(order);
		assertEquals("redirect:/orders", view);
	}
	
	@Test
	public void createThrowsExceptionInCaseOfBindingError() {
		
		Order order = new Order("x");
		BindingResult errors = new BindException(order, "order");
		errors.reject("some error");
		Model model = new ExtendedModelMap();
		model.addAttribute(order);
		
		try {
			controller.update(order, errors, model);
		} catch (ModelAndViewDefiningException e) {
			assertViewName(e.getModelAndView(), "editOrder");
			assertModelAttributeAvailable(e.getModelAndView(), "order");
			return;
		}
		
		fail("Should throw exception");
	}
}
