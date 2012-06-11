package com.gmail.at.zhuikov.aleksandr.it;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.sauce_ondemand.driver.SauceOnDemandSelenium;
import com.saucelabs.selenium.client.factory.SeleniumFactory;
import com.saucelabs.selenium.client.factory.spi.SeleniumFactorySPI;

public abstract class AbstractWebDriverTest {

	protected WebDriver driver;
	private final String relativeUrl;
	@Rule public TestName name = new TestName();
	
	public AbstractWebDriverTest(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}
	
	protected WebDriver createDriver() {
		
		String seleniumDriverUri = System.getenv("SELENIUM_DRIVER");
		seleniumDriverUri += "&job-name=" + getClass().getName() + "." + name.getMethodName();
		seleniumDriverUri += "&username=" + System.getenv("SAUCE_ONDEMAND_USERNAME");
		seleniumDriverUri += "&access-key=" + System.getenv("SAUCE_ONDEMAND_ACCESS_KEY");
		
		WebDriver driver = SeleniumFactory.createWebDriver(seleniumDriverUri,
				System.getenv("SELENIUM_STARTING_URL"));

		return driver;
//		DesiredCapabilities capabillities = DesiredCapabilities.firefox();
//		capabillities.setCapability("version", "5");
//		capabillities.setCapability("platform", Platform.XP);
//		capabillities.setCapability("name", getClass().getSimpleName() + "."
//				+ name.getMethodName());
//		capabillities.setCapability("capture-html", true);
//		capabillities.setCapability("build", System.getenv("BUILD_NUMBER"));
//
//        try {
//			return new RemoteWebDriver(
//			   new URL("http://cloudbees_reference:91d5e3b0-cbd6-4e23-acc9-ce14dc000565@ondemand.saucelabs.com:80/wd/hub"),
//			   capabillities);
//		} catch (MalformedURLException e) {
//			throw new RuntimeException(e);
//		}
	}
	
	@Before
	public void openPage() {
		driver = createDriver();
		driver.get("http://snapshot.reference.cloudbees.net/" + relativeUrl);
	}
	
	@After
	public void closePage() {
		driver.quit();
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
