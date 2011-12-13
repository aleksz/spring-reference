package com.gmail.at.servlet;

import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Transactional(readOnly = true)
	@RequestMapping(method = GET)
	public String list(Model model) {
		List<Order> orders = hibernateTemplate.findByCriteria(forClass(Order.class));
		LOG.info("Loaded " + orders.size() + " orders");
		model.addAttribute(orders);
		return "orders";
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "add", method = GET)
	public String add(Model model) {
		Order order = new Order();
		model.addAttribute(order);
		return "addOrder";
	}
	
	@Transactional
	@RequestMapping(method = POST)
	public String add(@Valid Order order, BindingResult errors) {
		
		if (errors.hasErrors()) {
			return "addOrder";
		}
		
		LOG.info("Adding new order " + order);
		hibernateTemplate.save(order);
		
		return "redirect:/order";
	}
}
