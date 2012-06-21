package com.gmail.at.zhuikov.aleksandr.view.json;

import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class OrderJsonViewTest {

	private @Mock ObjectMapper objectMapper;
	private @Mock JsonFactory jsonFactory;
	private @InjectMocks View view = new OrderJsonView();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		when(objectMapper.getJsonFactory()).thenReturn(jsonFactory);
	}
	
	@Test
	public void contentType() {
		assertEquals("application/json", view.getContentType());
	}

	@Test
	public void renderEmptyModel() throws Exception {
		Map<String, ?> model = new HashMap<String, Object>();
		view.render(model, request, response);
		verify(objectMapper).writeValue((JsonGenerator) null, emptyMap());
	}
	
	@Test
	public void renderModelWithOrder() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = new Order("x");
		model.put("order", order);
		view.render(model, request, response);
		verify(objectMapper).writeValue((JsonGenerator) null, order);
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
		verify(objectMapper).writeValue((JsonGenerator) null, order);
	}
}
