package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gmail.at.zhuikov.aleksandr.servlet.domain.Order;


@Controller
public class CreateOrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CreateOrderController.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@ExceptionHandler(BindException.class)
	public ModelAndView orderHasErrors(BindException e) {
		return new ModelAndView("addOrder", e.getModel());
	}

	@ModelAttribute
	public Order prepareOrder(@RequestParam(defaultValue = "") String customer) {
		return new Order(customer);
	}
	
	@Transactional
	@RequestMapping(value = "/orders", method = POST)
	public String create(@Valid Order order) {
		LOG.info("Adding new order " + order);
		hibernateTemplate.save(order);
		return "redirect:/orders";
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/orders/add", method = GET)
	public String createForm(Model model) {
		return "addOrder";
	}
}
