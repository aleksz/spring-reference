package com.gmail.at.servlet;

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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/order/{id}")
public class OrderControllerWithBackingObject {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderControllerWithBackingObject.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@ResponseStatus(value = NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public void objectNotFound() {
	}
	
	@ModelAttribute
	public Order loadCommandObject(@PathVariable Long id) {
		return hibernateTemplate.load(Order.class, id);
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(method = GET)
	public String get(Order order, Model model) {
		LOG.info("Loaded " + order);
		model.addAttribute(order);
		return "editOrder";
	}
	
	@Transactional
	@RequestMapping(method = DELETE)
	public String delete(Order order) {
		LOG.info("Deleting " + order);
		hibernateTemplate.delete(order);
		return "redirect:/order";
	}

	@Transactional
	@RequestMapping(method = POST)
	public String update(@Valid Order order, BindingResult errors) {
		
		if (errors.hasErrors()) {
			return "editOrder";
		}
		
		LOG.info("Updating " + order);
		hibernateTemplate.saveOrUpdate(order);
		
		return "redirect:/order";
	}
}
