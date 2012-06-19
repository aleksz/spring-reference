package com.gmail.at.zhuikov.aleksandr.rest.json;

import static junit.framework.Assert.assertEquals;
import static org.apache.http.auth.AuthScope.ANY_REALM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.gmail.at.zhuikov.aleksandr.rest.AbstractRestTest;
import com.gmail.at.zhuikov.aleksandr.rest.xml.MyResponseErrorHandler;
import com.gmail.at.zhuikov.aleksandr.rest.xml.MyResponseErrorHandler.MyHttpStatusCodeException;
import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyPage;
import com.gmail.at.zhuikov.aleksandr.servlet.MappingJackson2HttpMessageConverter;

public class JsonRestIT extends AbstractRestTest {

	private static final Logger LOG = LoggerFactory.getLogger(JsonRestIT.class);
	
	private RestTemplate restTemplate;
	
	@Before
	public void createRestTemplate() {
		restTemplate = new RestTemplate(createHttpClientFactory(server.getHost(), server.getPort()));
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
		objectMapper.enableDefaultTyping(DefaultTyping.JAVA_LANG_OBJECT, As.PROPERTY);
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new StringHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter() {{
			setObjectMapper(objectMapper);
		}});
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
	public void listOrders() throws JsonParseException, IOException {
		String result = restTemplate.getForObject(serverUrl + "/orders", String.class);
		LOG.info(result.toString());
		new ObjectMapper().readValue(result, JsonNode.class);
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
	public void createOrderAndPrintJson() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		System.out.println(restTemplate.postForObject(serverUrl + "/orders", order, String.class));
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
		assertNotNull(response);
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
		} catch (HttpClientErrorException e) {
			LOG.info(e.getResponseBodyAsString());
			assertEquals(BAD_REQUEST, e.getStatusCode());
			return;
		}
		fail("Should throw exception");
	}
	
	@Test
	public void createWrongOrderWithCustomErrorHandler() throws JsonParseException, JsonMappingException, IOException {
		
		restTemplate.setErrorHandler(new MyResponseErrorHandler(
				restTemplate.getMessageConverters()));

		
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

//		assertTrue(hasText(result.getRemoteAddr()));
		assertEquals("super customer", result.getCustomer());
		assertEquals("customer@example.com", result.getEmail());
		assertNotNull(result.getDate());
		assertNotNull(result.getId());
		assertEquals(response.getId(), result.getId());
	}
}
