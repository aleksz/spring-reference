package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static junit.framework.Assert.fail;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class CreateOrderControllerTest {

	private CreateOrderController controller = new CreateOrderController();
	
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
