package com.gmail.at.zhuikov.aleksandr.it;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class LocaleIT extends AbstractWebDriverTest {

	public LocaleIT() {
		super("orders");
	}

	@Override
	protected WebDriver createDriver() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", "ru" );
		return new FirefoxDriver(profile);
	}
	
	@Test
	public void test() throws Exception {
		driver.findElement(By.partialLinkText("Добавить новый заказ"));
	}
}
