package com.gmail.at.zhuikov.aleksandr.it;

import static java.util.Arrays.asList;
import static org.apache.http.auth.AuthScope.ANY_REALM;
import static org.apache.http.client.protocol.ClientContext.AUTH_CACHE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_XML;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

public class RestIT {

	private static final Logger LOG = LoggerFactory.getLogger(RestIT.class);
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
	public void listOrdersJSON() throws JsonParseException, IOException {
		String result = restTemplate.getForObject(SERVER + "/orders", String.class);
		LOG.info(result);
		new ObjectMapper().readValue(result, JsonNode.class);
	}
	
	@Test
	public void listOrdersXML() throws ParserConfigurationException, SAXException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(asList(APPLICATION_XML));
		String result = restTemplate.exchange(
				SERVER + "/orders",
				GET, 
				new HttpEntity<String>(headers),
				String.class).getBody();
		
		LOG.info(result);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.parse(new ByteArrayInputStream(result.getBytes()));
	}
}
