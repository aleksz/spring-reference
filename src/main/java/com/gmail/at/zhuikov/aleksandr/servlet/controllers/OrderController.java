package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.gmail.at.zhuikov.aleksandr.servlet.domain.Order;
import com.gmail.at.zhuikov.aleksandr.servlet.repository.OrderRepository;


@Controller
@RequestMapping("/orders/{id}")
public class OrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@ResponseStatus(value = NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public void objectNotFound() {
	}
	
	@ModelAttribute("remoteAddr")
	public String getRemoteAddr(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
	
	@ModelAttribute
	public Order prepareOrder(@PathVariable Long id) {
		return orderRepository.load(id);
	}
	
	@RequestMapping(method = GET)
	public String read(Order order) {
		LOG.info("Loaded " + order);
		return "editOrder";
	}
	
	@RequestMapping(method = DELETE)
	public String delete(Order order) {
		LOG.info("Deleting " + order);
		orderRepository.delete(order);
		return "redirect:/orders";
	}
	
	@RequestMapping(method = POST)
	public String update(@Valid Order order, BindingResult errors, Model model) throws ModelAndViewDefiningException {
		
		if (errors.hasErrors()) {
			throw new ModelAndViewDefiningException(new ModelAndView("editOrder", model.asMap()));
		}
		
		LOG.info("Updating " + order);
		orderRepository.update(order);
		
		return "redirect:/orders";
	}
}
