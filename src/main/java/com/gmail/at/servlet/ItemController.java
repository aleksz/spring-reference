package com.gmail.at.servlet;

import static org.springframework.http.HttpStatus.NOT_FOUND;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/order/{orderId}/item")
public class ItemController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@ResponseStatus(value = NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public void objectNotFound() {
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/add", method = GET)
	public String add(@PathVariable Long orderId, Model model) {
		Item item = new Item((Order) hibernateTemplate.load(Order.class, orderId));
		model.addAttribute(item);
		return "addItem";
	}
	
	@Transactional
	@RequestMapping(method = POST)
	public String add(@PathVariable Long orderId, @Valid Item item, BindingResult errors) {
		
		if (errors.hasErrors()) {
			return "addItem";
		}
		
		LOG.info("Adding item " + item);
		
		Order order = (Order) hibernateTemplate.load(Order.class, orderId);
		order.getItems().add(item);
		hibernateTemplate.saveOrUpdate(order);
		
		return "redirect:/order/" + orderId;
	}
	
}
