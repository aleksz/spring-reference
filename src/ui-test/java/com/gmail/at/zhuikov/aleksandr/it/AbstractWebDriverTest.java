package com.gmail.at.zhuikov.aleksandr.it;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class AbstractWebDriverTest {

	protected WebDriver driver;
	private final String relativeUrl;
	
	public AbstractWebDriverTest(String relativeUrl) {
		this.relativeUrl = relativeUrl;
		driver = createDriver();
	}
	
	protected WebDriver createDriver() {
		return new FirefoxDriver();
	}
	
	@Before
	public void openPage() {
		driver.get("http://localhost:8080/spring-reference/" + relativeUrl);
	}
	
	@After
	public void closePage() {
		driver.close();
	}
	
	protected String getUniqueCharString() {
		String millis = String.valueOf(System.currentTimeMillis());
	    
	    char[] random = new char[millis.length()];
	    for (int i = 0; i < millis.length(); i++) {
	      random[i] = (char) (millis.charAt(i) - '0' + 'a');
	    }

		return new String(random);
	}
}
