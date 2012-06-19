package com.gmail.at.zhuikov.aleksandr.rest;

import static org.springframework.util.StringUtils.hasText;

import java.net.MalformedURLException;
import java.net.URL;

public class AbstractRestTest {

	protected String serverUrl;
	protected URL server;
	
	public AbstractRestTest() {
		serverUrl = System.getenv("SELENIUM_STARTING_URL");
		
		if (!hasText(serverUrl)) {
			serverUrl = System.getProperty("DEFAULT_SELENIUM_STARTING_URL");
		}
		
		try {
			server = new URL(serverUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
