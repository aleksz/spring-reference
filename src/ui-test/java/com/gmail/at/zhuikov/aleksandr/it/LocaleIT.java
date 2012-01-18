package com.gmail.at.zhuikov.aleksandr.it;

import static junit.framework.Assert.assertEquals;
import static org.openqa.selenium.support.PageFactory.initElements;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.gmail.at.zhuikov.aleksandr.it.page.OrdersPage;

public class LocaleIT extends AbstractWebDriverTest {

	public LocaleIT() {
		super("orders");
	}

	@Override
	protected WebDriver createDriver() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", "ru");
		return new FirefoxDriver(profile);
	}
	
	@Test
	public void checkLocalizationOnOrdersPage() throws Exception {
		OrdersPage page = initElements(driver, OrdersPage.class);
		assertEquals("Добавить новый заказ", page.getAddNewOrderLinkText());
	}
}
