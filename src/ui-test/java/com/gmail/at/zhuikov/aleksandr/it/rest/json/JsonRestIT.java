package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import static junit.framework.Assert.assertEquals;
import static org.apache.http.auth.AuthScope.ANY_REALM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.StringUtils.hasText;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.gmail.at.zhuikov.aleksandr.root.domain.Item;
import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class JsonRestIT {

	private static final Logger LOG = LoggerFactory.getLogger(JsonRestIT.class);
	private static final String SERVER = "http://localhost:8080/spring-reference";
	
	private RestTemplate restTemplate;
	
	@Before
	public void createRestTemplate() {
		restTemplate = new RestTemplate(createHttpClientFactory("localhost", 8080));
	}
	
	private HttpComponentsClientHttpRequestFactory createHttpClientFactory(
			String serverName, int serverPort) {

		DefaultHttpClient client = new DefaultHttpClient();
		client.getCredentialsProvider().setCredentials(
		        new AuthScope(serverName, serverPort, ANY_REALM), 
		        new UsernamePasswordCredentials("restuser", "ohW559f5"));

		return new HttpComponentsClientHttpRequestFactory(client);
	}
	
	@Test
	public void listOrdersReturnsJSON() throws JsonParseException, IOException {
		String result = restTemplate.getForObject(SERVER + "/orders", String.class);
		LOG.info(result.toString());
		new ObjectMapper().readValue(result, JsonNode.class);
	}
	
	@Test
	public void listOrdersReturnsJsonPage() throws JsonParseException, IOException {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		restTemplate.postForLocation(SERVER + "/orders", order);
		ListOrdersResult result = restTemplate.getForObject(SERVER + "/orders", ListOrdersResult.class);
		LOG.info(result.toString());
		assertEquals(0, result.getPage().getNumber());
		assertEquals(20, result.getPage().getSize());
		assertFalse(result.getPage().getContent().isEmpty());
		assertTrue(result.getPage().getNumberOfElements() > 0);
		assertTrue(result.getPage().getTotalElements() >= 1);
		assertTrue(result.getPage().getTotalPages() >= 1);
		assertNotNull(result.getPage().getSort());
		assertEquals(DESC, result.getPage().getSort().getOrderFor("date").getDirection());
	}
	
	@Test
	public void createOrder() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		GetOrderResponse response = restTemplate.postForObject(SERVER + "/orders", order, GetOrderResponse.class);
		assertNotNull(response);
		assertEquals(order, response.getOrder());
		assertNotNull(response.getOrder().getId());
	}
	
	@Test
	public void createOrderWithItem() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		new Item(order, "x", 1).setQuantity(1);
		GetOrderResponse response = restTemplate.postForObject(SERVER + "/orders", order, GetOrderResponse.class);
		assertNotNull(response);
		assertEquals(order, response.getOrder());
		assertNotNull(response.getOrder().getId());
	}
	
	@Test
	public void createOrderAndGetLocation() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		LOG.info(restTemplate.postForLocation(SERVER + "/orders", order).toString());
	}
	
	@Test
	public void createWrongOrder() throws JsonParseException, JsonMappingException, IOException {
		Order request = new Order("2444");
		request.setEmail("customer@example.com");
		try {
			restTemplate.postForObject(SERVER + "/orders", request, GetOrderResponse.class);
		} catch (HttpClientErrorException e) {
			LOG.info(e.getResponseBodyAsString());
			OrderErrorResponse response = new ObjectMapper().readValue(
					e.getResponseBodyAsByteArray(),
					OrderErrorResponse.class);
			assertEquals(request, response.getOrder());
			assertFalse(response.getErrors().isEmpty());
			assertTrue(response.getErrors().get(0) instanceof FieldError);
			FieldError error = (FieldError) response.getErrors().get(0);
			assertEquals("order", error.getObjectName());
			assertEquals("customer", error.getField());
		    assertNotNull(error.getDefaultMessage());
		    assertEquals("2444", error.getRejectedValue());
			assertEquals(BAD_REQUEST, e.getStatusCode());
			return;
		}
		fail("Should throw exception");
	}
	
	@Test
	public void getOrder() {
		Order order = new Order("super customer");
		order.setEmail("customer@example.com");
		GetOrderResponse response = restTemplate.postForObject(SERVER
				+ "/orders", order, GetOrderResponse.class);

		GetOrderResponse result = restTemplate.getForObject(SERVER + "/orders/"
				+ response.getOrder().getId(), GetOrderResponse.class);

		assertTrue(hasText(result.getRemoteAddr()));
		assertEquals("super customer", result.getOrder().getCustomer());
		assertEquals("customer@example.com", result.getOrder().getEmail());
		assertNotNull(result.getOrder().getDate());
		assertNotNull(result.getOrder().getId());
		assertEquals(response.getOrder().getId(), result.getOrder().getId());
	}
	
	public static class GetOrderResponse {
		
		private Order order;
		private String remoteAddr;
		
		@JsonCreator
		public GetOrderResponse(
				@JsonProperty("order") Order order,
				@JsonProperty("remoteAddr") String remoteAddr) {
			this.order = order;
			this.remoteAddr = remoteAddr;
		}
		
		public Order getOrder() {
			return order;
		}
		
		public String getRemoteAddr() {
			return remoteAddr;
		}
	}
}
