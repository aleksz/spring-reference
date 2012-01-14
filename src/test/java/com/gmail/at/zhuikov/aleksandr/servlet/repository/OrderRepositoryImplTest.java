package com.gmail.at.zhuikov.aleksandr.servlet.repository;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.respository.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/root-context.xml")
public class OrderRepositoryImplTest {

	@Inject OrderRepository repository;
	
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
