package com.gmail.at.zhuikov.aleksandr.it.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {

	protected WebDriver driver;
	
	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}
	
	protected boolean hasElement(By selector) {
		return driver.findElements(selector).size() != 0;
	}
}
