package com.gmail.at.zhuikov.aleksandr.view.xml;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.oxm.Marshaller;
import org.springframework.web.servlet.View;

import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyPage;

public class OrdersPageXmlViewTest {

	private @Mock Marshaller marshaller;
	private @InjectMocks View view = new OrdersPageXmlView();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test() throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", new PageImpl<String>(asList("a", "b")));
		view.render(model, request, response);
		verify(marshaller).marshal(Mockito.isA(XmlFriendlyPage.class), Mockito.isA(Result.class));
	}

}
