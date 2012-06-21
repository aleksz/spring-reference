package com.gmail.at.zhuikov.aleksandr.view.xml;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.oxm.Marshaller;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.View;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class OrderXmlViewTest {

	private @Mock Marshaller marshaller;
	private @InjectMocks View view = new OrderXmlView();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		when(marshaller.supports(Order.class)).thenReturn(true);
	}
	
	@Test
	public void contentType() {
		assertEquals("application/xml", view.getContentType());
	}

	@Test
	public void renderOrder() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = new Order("x");
		model.put("order", order);
		
		view.render(model, request, response);

		verify(marshaller).marshal(Mockito.eq(order), Mockito.isA(Result.class));
	}
	
	@Test
	public void renderModelWithBindingError() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = new Order("x");
		BindException bindingResult = new BindException(order, "order");
		bindingResult.rejectValue("email", "empty");
		model.put("org.springframework.validation.BindingResult.order",
				bindingResult);
		
		view.render(model, request, response);
	}
	
	@Test
	public void renderModelWithOrderAndBindingResultWithoutErrors() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = new Order("x");
		model.put("order", order);
		model.put("org.springframework.validation.BindingResult.order",
				new BindException(order, "order"));
		view.render(model, request, response);
		verify(marshaller).marshal(Mockito.eq(order), Mockito.isA(Result.class));
	}

}
