package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;

public class CreateOrderControllerTest {

	@Mock 
	private OrderRepository orderRepository;
	
	@InjectMocks 
	private CreateOrderController controller = new CreateOrderController();
	
	@Before
	public void injectMocks() {
		initMocks(this);
	}
	
	@Test
	public void createForm() {
		assertEquals("addOrder", controller.createForm());
	}
	
	@Test
	public void prepareOrder() {
		Order order = controller.prepareOrder("x");
		assertEquals("x", order.getCustomer());
	}
	
	@Test
	public void create() throws ModelAndViewDefiningException {
		Order order = new Order("x");
		BindingResult errors = new BindException(order, "order");
		Model model = new ExtendedModelMap();
		
		String view = controller.create(order, errors, model);
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
			controller.create(order, errors, model);
		} catch (ModelAndViewDefiningException e) {
			assertViewName(e.getModelAndView(), "addOrder");
			assertModelAttributeAvailable(e.getModelAndView(), "order");
			return;
		}
		
		fail("Shoudl throw exception");
	}

}
