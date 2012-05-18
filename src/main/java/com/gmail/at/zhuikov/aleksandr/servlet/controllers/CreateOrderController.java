package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URI;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;


@Controller
public class CreateOrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CreateOrderController.class);
	
	@Inject
	private OrderRepository orderRepository;
	
	@ModelAttribute
	public Order prepareOrder(@RequestParam(defaultValue = "") String customer) {
		return new Order(customer);
	}
	
	@RequestMapping(value = "/orders", method = POST, 
			consumes = { "application/json", "application/xml" },
			produces = { "application/json", "application/xml" })
	public String createFromBody(@Valid @RequestBody Order order) {
		saveOrder(order);
		return "redirect:/orders/" + order.getId();
	}
	
	@RequestMapping(value = "/orders", method = POST, consumes = { "application/json", "application/xml" })
	public ResponseEntity<Void> createFromBodyAndReturnLocation(
			@Valid @RequestBody Order order, HttpServletRequest request) {

		saveOrder(order);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(getOrderLocation(request, order));
		return new ResponseEntity<Void>(headers, CREATED);
	}

	@RequestMapping(value = "/orders", method = POST)
	public String create(@Valid Order order) {
		saveOrder(order);
		return "redirect:/orders"; 
	}
	
	@RequestMapping(value = "/orders/add", method = GET)
	public String createForm() {
		return "addOrder";
	}
	
	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ModelAndView handleInvalidOrder(MethodArgumentNotValidException e) {
		return new ModelAndView("addOrder", e.getBindingResult().getModel());
	}
	
	@ExceptionHandler
	@ResponseStatus(BAD_REQUEST)
	public ModelAndView handleInvalidOrder(BindException e) {
		return new ModelAndView("addOrder", e.getBindingResult().getModel());
	} 
	
	private URI getOrderLocation(HttpServletRequest request, Order order) {
		return ServletUriComponentsBuilder.fromServletMapping(request)
				.path("/orders/{id}").build().expand(order.getId()).toUri();
	}
	
	private void saveOrder(Order order) {
		LOG.info("Adding new order " + order);
		orderRepository.save(order);
	}
}
