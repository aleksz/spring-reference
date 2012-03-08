package com.gmail.at.zhuikov.aleksandr.it.rest.xml;

import static java.util.Arrays.asList;
import static org.apache.http.auth.AuthScope.ANY_REALM;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_XML;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

public class XmlRestIT {

	private static final Logger LOG = LoggerFactory.getLogger(XmlRestIT.class);
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
	public void listOrdersXML() throws ParserConfigurationException, SAXException, IOException {
		String result = restTemplate.exchange(
				SERVER + "/orders",
				GET, 
				new HttpEntity<String>(createXmlHeaders()),
				String.class).getBody();
		
		LOG.info(result);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.parse(new ByteArrayInputStream(result.getBytes()));
	}

	private HttpHeaders createXmlHeaders() {
		return createHeaders(asList(APPLICATION_XML));
	}
	
	private HttpHeaders createHeaders(List<MediaType> types) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(types);
		return headers;
	}
}
