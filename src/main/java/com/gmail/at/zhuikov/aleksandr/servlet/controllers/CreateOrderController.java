package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.respository.OrderRepository;


@Controller
public class CreateOrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CreateOrderController.class);
	
	@Inject
	private OrderRepository orderRepository;
	
	@ModelAttribute
	public Order prepareOrder(@RequestParam(defaultValue = "") String customer) {
		return new Order(customer);
	}
	
	@RequestMapping(value = "/orders", method = POST)
	public String create(@Valid Order order, BindingResult errors, Model model)
			throws ModelAndViewDefiningException {

		if (errors.hasErrors()) {
			throw new ModelAndViewDefiningException(
					new ModelAndView("addOrder", model.asMap()));
		}

		LOG.info("Adding new order " + order);
		orderRepository.save(order);
		return "redirect:/orders";
	}
	
	@RequestMapping(value = "/orders/add", method = GET)
	public String createForm(Model model) {
		return "addOrder";
	}
}
