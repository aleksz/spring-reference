package com.gmail.at.zhuikov.aleksandr.servlet.repository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/root-context.xml")
@Transactional
public class OrderRepositoryImplTest {

	@Inject OrderRepository repository;
	@PersistenceContext EntityManager em;
	
	@Test
	public void loadMissingObject() {
		assertNull(repository.findOne(1234234L));
	}
	
	@Test
	public void getAll() {
		Order order1 = new Order("theCustomer");
		order1.setEmail("mail@example.com");
		em.persist(order1);
		
		Order order2 = new Order("secondCustomer");
		order2.setEmail("mail@example.com");
		em.persist(order2);
		
		List<Order> all = repository.findAll();
		assertEquals(2, all.size());
		assertTrue(all.contains(order1));
		assertTrue(all.contains(order2));
	}
	
	@Test
	public void update() {
		Order order = new Order("theCustomer");
		order.setEmail("mail@example.com");
		em.persist(order);
		order.setEmail("changed@example.com");
		repository.save(order);
		order = em.find(Order.class, order.getId());
		assertEquals("changed@example.com", order.getEmail());
	}
	
	@Test
	public void save() {
		Order order = new Order("theCustomer");
		order.setEmail("mail@example.com");
		repository.save(order);
		Order reloaded = em.find(Order.class, order.getId());
		assertEquals(order, reloaded);
	}
	
	@Test
	public void delete() {
		Order order = new Order("theCustomer");
		order.setEmail("mail@example.com");
		em.persist(order);
		repository.delete(order);
		assertNull(em.find(Order.class, order.getId()));
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void saveValidatesOrder() {
		Order invalidOrder = new Order("");
		repository.save(invalidOrder);
	}

	@Test(expected = ConstraintViolationException.class)
	public void updateValidatesOrder() {
		Order invalidOrder = new Order("");
		repository.save(invalidOrder);
	}
}
