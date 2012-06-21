package com.gmail.at.zhuikov.aleksandr.view.json;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class OrderJsonViewTest {

	private View view = new OrderJsonView();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Test
	public void contentType() {
		assertEquals("application/json", view.getContentType());
	}

	@Test
	public void renderEmptyModel() throws Exception {
		Map<String, ?> model = new HashMap<String, Object>();
		view.render(model, request, response);
		assertEquals("{}", response.getContentAsString());
	}
	
	@Test
	public void renderModelWithOrder() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("order", new Order("x"));
		view.render(model, request, response);
		assertTrue(
				response.getContentAsString(),
				response.getContentAsString()
						.matches(
								"\\{\"id\":null," +
								"\"customer\":\"x\"," +
								"\"email\":null," +
								"\"date\":\\d{13}," +
								"\"items\":\\[\\]\\}"));
	}
}
