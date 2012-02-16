package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;


@Controller
public class OrdersController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrdersController.class);
	
	@Inject
	private OrderRepository orderRepository;
	
	@ModelAttribute
	public Page<Order> loadAllOrders(@RequestParam(required = false, defaultValue = "0") int page) {
		return orderRepository.findAll(new PageRequest(page, 20, DESC, "date"));
	}
	
	@RequestMapping(value = "/orders", method = GET)
	public String list(Page<Order> orders) {
		LOG.info("Loaded " + orders);
		return "orders";
	}
}
