package com.gmail.at;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly = true)
	@RequestMapping(method = GET)
	public String list(Model model) {
		Session session = sessionFactory.getCurrentSession();
		List<Order> orders = session.createCriteria(Order.class).list();
		LOG.info("Loaded " + orders.size() + " orders");
		model.addAttribute(orders);
		return "orders";
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/{id}", method = GET)
	public String get(@PathVariable Long id, Model model) {
		Session session = sessionFactory.getCurrentSession();
		Order order = (Order) session.load(Order.class, id);
		LOG.info("Loaded " + order);
		model.addAttribute(order);
		return "editOrder";
	}
	
	@Transactional
	@RequestMapping(value = "/{id}", method = DELETE)
	public String delete(Order order) {
		LOG.info("Deleting " + order);
		Session session = sessionFactory.getCurrentSession();
		session.delete(order);
		return "redirect:/order";
	}
	
	@Transactional
	@RequestMapping(value = "/{id}", method = POST)
	public String update(@Valid Order order, BindingResult errors) {
		
		if (errors.hasErrors()) {
			return "editOrder";
		}
		
		LOG.info("Updating " + order);
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(order);
		return "redirect:/order";
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value = "/add", method = GET)
	public String add(Model model) {
		Order order = new Order();
		model.addAttribute(order);
		return "addOrder";
	}
	
	@Transactional
	@RequestMapping(method = PUT)
	public String add(@Valid Order order, BindingResult errors) {
		
		if (errors.hasErrors()) {
			return "addOrder";
		}
		
		Session session = sessionFactory.getCurrentSession();
		session.save(order);
		return "redirect:/order";
	}
	
	@ResponseStatus(value = NOT_FOUND)
	@ExceptionHandler(ObjectNotFoundException.class)
	public void objectNotFound() {
	}
}
