package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gmail.at.zhuikov.aleksandr.servlet.domain.Order;


@Controller
@RequestMapping("/orders/{id}")
public class OrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@ResponseStatus(value = NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public void objectNotFound() {
	}
	
	@ExceptionHandler(BindException.class)
	public ModelAndView orderHasErrors(BindException e) {
		return new ModelAndView("editOrder", e.getModel());
	}
	
	@ModelAttribute
	public Order prepareOrder(@PathVariable Long id) {
		return hibernateTemplate.load(Order.class, id);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(method = GET)
	public String read(Order order) {
		LOG.info("Loaded " + order);
		return "editOrder";
	}
	
	@Transactional
	@RequestMapping(method = DELETE)
	public String delete(Order order) {
		LOG.info("Deleting " + order);
		hibernateTemplate.delete(order);
		return "redirect:/orders";
	}
	
	@Transactional
	@RequestMapping(method = POST)
	public String update(@Valid Order order) {
		LOG.info("Updating " + order);
		hibernateTemplate.saveOrUpdate(order);
		return "redirect:/orders";
	}
}
