package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;

@Controller
@RequestMapping("/orders/{orderId}/items")
public class ItemsController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ItemsController.class);
	
	@Inject
	private OrderRepository orderRepository;
	
	@ExceptionHandler(BindException.class)
	public ModelAndView itemHasErrors(BindException e) {
		return new ModelAndView("addItem", e.getModel());
	}
	
	/**
	 * We are binding directly to fields, avoiding setters as {@link Item} is
	 * immutable.
	 */
	@InitBinder
	public void initFieldAccess(DataBinder binder) {
		binder.initDirectFieldAccess();
	}
	
	@ModelAttribute
	public Item prepareItem(@PathVariable Long orderId) throws OrderNotFoundException {
		Order order = orderRepository.findOne(orderId);
		
		if (order == null) {
			throw new OrderNotFoundException();
		}
		
		return new Item(order, "", 0);
	}
	
	@RequestMapping(value = "add", method = GET)
	public String createForm() {
		return "addItem";
	}
	
	@RequestMapping(method = POST)
	public String create(@Valid Item item) {
		LOG.info("Adding item " + item);
		orderRepository.save(item.getOrder());
		return "redirect:/orders/" + item.getOrder().getId();
	}
}