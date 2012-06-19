package com.gmail.at.zhuikov.aleksandr.rest;

import static org.springframework.util.StringUtils.hasText;

public class AbstractRestTest {

	protected String serverUrl;
	
	public AbstractRestTest() {
		serverUrl = System.getenv("SELENIUM_STARTING_URL");
		
		if (!hasText(serverUrl)) {
			serverUrl = System.getProperty("DEFAULT_SELENIUM_STARTING_URL");
		}
	}
	
}
