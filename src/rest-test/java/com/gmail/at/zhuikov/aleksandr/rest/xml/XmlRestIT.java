package com.gmail.at.zhuikov.aleksandr.rest.xml;

import static junit.framework.Assert.assertEquals;
import static org.apache.http.auth.AuthScope.ANY_REALM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.at.zhuikov.aleksandr.rest.AbstractRestTest;
import com.gmail.at.zhuikov.aleksandr.rest.xml.MyResponseErrorHandler.MyHttpStatusCodeException;
import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyErrors;
import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyPage;

public class XmlRestIT extends AbstractRestTest {

	private static final Logger LOG = LoggerFactory.getLogger(XmlRestIT.class);
	
	private RestTemplate restTemplate;
	
	@Before
	public void createRestTemplate() {
		restTemplate = new RestTemplate(createHttpClientFactory(server.getHost(), server.getPort()));
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new StringHttpMessageConverter());
		converters.add(new MarshallingHttpMessageConverter(new Jaxb2Marshaller() {{
			setClassesToBeBound(XmlFriendlyErrors.class, Order.class, XmlFriendlyPage.class);
		}}));
		restTemplate.setMessageConverters(converters);
	}
	
	private HttpComponentsClientHttpRequestFactory createHttpClientFactory(
			String serverUrlName, int serverUrlPort) {

		DefaultHttpClient client = new DefaultHttpClient();
		client.getCredentialsProvider().setCredentials(
		        new AuthScope(serverUrlName, serverUrlPort, ANY_REALM), 
		        new UsernamePasswordCredentials("restuser", "ohW559f5"));

		return new HttpComponentsClientHttpRequestFactory(client);
	}
	
	@Test
	public void listOrders() throws ParserConfigurationException, SAXException, IOException {
		String result = restTemplate.getForObject(
				serverUrl + "/orders",
				String.class);
		
		LOG.info(result);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.parse(new ByteArrayInputStream(result.getBytes()));
	}
	
	@Test
	public void listOrdersReturnsPage() throws JsonParseException, IOException {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		restTemplate.postForLocation(serverUrl + "/orders", order);
		Page<Order> result = restTemplate.getForObject(serverUrl + "/orders", XmlFriendlyPage.class);
		LOG.info(result.toString());
		assertEquals(0, result.getNumber());
		assertEquals(20, result.getSize());
		assertFalse(result.getContent().isEmpty());
		assertTrue(result.getNumberOfElements() > 0);
		assertTrue(result.getTotalElements() >= 1);
		assertTrue(result.getTotalPages() >= 1);
		assertNotNull(result.getSort());
		assertEquals(DESC, result.getSort().getOrderFor("date").getDirection());
	}
	
	@Test
	public void createOrder() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		Order response = restTemplate.postForObject(serverUrl + "/orders", order, Order.class);
		assertNotNull(response);
		assertEquals(order, response);
		assertNotNull(response.getId());
	}
	
	@Test
	public void createOrderWithItem() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		new Item(order, "x", 1).setQuantity(1);
		Order response = restTemplate.postForObject(serverUrl + "/orders", order, Order.class);
		assertEquals(order, response);
		assertNotNull(response.getId());
	}
	
	@Test
	public void createOrderAndGetLocation() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		LOG.info(restTemplate.postForLocation(serverUrl + "/orders", order).toString());
	}
	
	@Test
	public void createWrongOrder() throws JsonParseException, JsonMappingException, IOException {
		Order request = new Order("2444");
		request.setEmail("customer@example.com");
		try {
			restTemplate.postForObject(serverUrl + "/orders", request, Order.class);
		} catch (HttpStatusCodeException e) {
			LOG.info(e.getResponseBodyAsString());
			assertEquals(BAD_REQUEST, e.getStatusCode());
			return;
		}
		fail("Should throw exception");
	}
	
	@Test
	public void createWrongOrderWithCustomErrorHandler() throws JsonParseException, JsonMappingException, IOException {
		restTemplate.setErrorHandler(new MyResponseErrorHandler(restTemplate.getMessageConverters()));
		
		Order request = new Order("2444");
		request.setEmail("customer@example.com");
		try {
			restTemplate.postForObject(serverUrl + "/orders", request, Order.class);
		} catch (MyHttpStatusCodeException e) {
			assertEquals(request, e.getErrorBody().getTarget());
			assertFalse(e.getErrorBody().getErrors().isEmpty());
			assertTrue(e.getErrorBody().getErrors().get(0) instanceof FieldError);
			FieldError error = (FieldError) e.getErrorBody().getErrors().get(0);
			assertEquals("order", error.getObjectName());
			assertEquals("customer", error.getField());
		    assertNotNull(error.getDefaultMessage());
		    assertEquals("2444", error.getRejectedValue());
		    assertTrue(error.getCodes().length > 0);
			assertEquals(BAD_REQUEST, e.getStatusCode());
			return;
		}
		fail("Should throw exception");
	}
	
	@Test
	public void getOrder() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		Order response = restTemplate.postForObject(serverUrl
				+ "/orders", order, Order.class);

		Order result = restTemplate.getForObject(serverUrl + "/orders/"
				+ response.getId(), Order.class);

		assertEquals("super customer", result.getCustomer());
		assertEquals("customer@example.com", result.getEmail());
		assertNotNull(result.getDate());
		assertNotNull(result.getId());
		assertEquals(response.getId(), result.getId());
	}
}
