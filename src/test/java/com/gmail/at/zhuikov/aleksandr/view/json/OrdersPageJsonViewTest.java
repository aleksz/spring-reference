package com.gmail.at.zhuikov.aleksandr.view.json;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyPage;

public class OrdersPageJsonViewTest {

	private @Mock ObjectMapper objectMapper;
	private @Mock JsonFactory jsonFactory;
	private @InjectMocks View view = new OrdersPageJsonView();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		when(objectMapper.getJsonFactory()).thenReturn(jsonFactory);
	}
	
	@Test
	public void test() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", new PageImpl<String>(asList("a", "b")));
		view.render(model, request, response);
		verify(objectMapper).writeValue(Mockito.isNull(JsonGenerator.class),
				Mockito.isA(XmlFriendlyPage.class));
	}

}
