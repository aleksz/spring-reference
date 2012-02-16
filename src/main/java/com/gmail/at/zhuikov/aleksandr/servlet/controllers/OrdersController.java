package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;


@Controller
public class OrdersController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrdersController.class);
	
	@Inject
	private OrderRepository orderRepository;
	
	@ModelAttribute
	public List<Order> loadAllOrders() {
		return orderRepository.findAll();
	}
	
	@RequestMapping(value = "/orders", method = GET)
	public String list(List<Order> orders) {
		LOG.info("Loaded " + orders.size() + " orders");
		return "orders";
	}
}
