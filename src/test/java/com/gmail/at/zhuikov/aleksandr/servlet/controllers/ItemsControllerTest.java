package com.gmail.at.zhuikov.aleksandr.servlet.controllers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.repository.OrderRepository;

public class ItemsControllerTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private DataBinder binder;
	
	@InjectMocks
	private ItemsController controller = new ItemsController();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void initBinder() {
		controller.initFieldAccess(binder);
		verify(binder).initDirectFieldAccess();
	}
	
	@Test
	public void itemHasErrors() {
		Order order = new Order("x");
		Item item = new Item(order, "p", 1);
		BindException e = new BindException(item, "item");
		ModelAndView mav = controller.itemHasErrors(e);
		assertViewName(mav, "addItem");
		assertModelAttributeAvailable(mav, "item");
	}
	
	@Test
	public void prepareItem() throws OrderNotFoundException {
		Order order = new Order("x");
		when(orderRepository.findOne(2L)).thenReturn(order);
		Item item = controller.prepareItem(2L);
		assertEquals(order, item.getOrder());
		assertEquals("", item.getProduct());
		assertEquals(0.0, item.getPrice());
	}
	
	@Test(expected = OrderNotFoundException.class)
	public void prepareItemThrowsExceptionIfOrderNotFound() throws OrderNotFoundException {
		when(orderRepository.findOne(2L)).thenReturn(null);
		controller.prepareItem(2L);
	}
	
	@Test
	public void create() {
		Order order = new Order("x");
		ReflectionTestUtils.setField(order, "id", 2L);
		String view = controller.create(new Item(order, "p", 1.1));
		verify(orderRepository).save(order);
		assertEquals("redirect:/orders/2", view);
	}
	
	@Test
	public void createForm() {
		assertEquals("addItem", controller.createForm());
	}

}
