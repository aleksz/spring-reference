package com.gmail.at.zhuikov.aleksandr.servlet.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.respository.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/root-context.xml")
@Transactional
public class OrderRepositoryImplTest {

	@Inject OrderRepository repository;
	@Inject HibernateTemplate hibernate;
	
	@Test(expected = ObjectNotFoundException.class)
	public void loadMissingObject() {
		Order order = repository.load(1234234);
		order.getId();
	}
	
	@Test
	public void getAll() {
		Order order1 = new Order("theCustomer");
		order1.setEmail("mail@example.com");
		hibernate.save(order1);
		
		Order order2 = new Order("secondCustomer");
		order2.setEmail("mail@example.com");
		hibernate.save(order2);
		
		List<Order> all = repository.getAll();
		assertEquals(2, all.size());
		assertTrue(all.contains(order1));
		assertTrue(all.contains(order2));
	}
	
	@Test
	public void update() {
		Order order = new Order("theCustomer");
		order.setEmail("mail@example.com");
		hibernate.save(order);
		order.setEmail("changed@example.com");
		repository.update(order);
		order = hibernate.load(Order.class, order.getId());
		assertEquals("changed@example.com", order.getEmail());
	}
	
	@Test
	public void save() {
		Order order = new Order("theCustomer");
		order.setEmail("mail@example.com");
		repository.save(order);
		Order reloaded = hibernate.load(Order.class, order.getId());
		assertEquals(order, reloaded);
	}
	
	@Test
	public void delete() {
		Order order = new Order("theCustomer");
		order.setEmail("mail@example.com");
		hibernate.save(order);
		repository.delete(order);
		assertNull(hibernate.get(Order.class, order.getId()));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveValidatesOrder() {
		Order invalidOrder = new Order("");
		repository.save(invalidOrder);
	}

	@Test(expected = ConstraintViolationException.class)
	public void updateValidatesOrder() {
		Order invalidOrder = new Order("");
		repository.update(invalidOrder);
	}
}
