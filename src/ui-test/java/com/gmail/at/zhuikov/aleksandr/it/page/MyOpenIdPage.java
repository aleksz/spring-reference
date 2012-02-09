package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyOpenIdPage extends AbstractPage {

	@FindBy(name = "password")
	private WebElement passwordInput;
	
	@FindBy(id = "signin_button")
	private WebElement signInButton;
	
	public MyOpenIdPage(WebDriver driver) {
		super(driver);
	}

	public MyOpenIdPage setPassword(String password) {
		passwordInput.clear();
		passwordInput.sendKeys(password);
		return this;
	}
	
	public MyOpenIdPageStep2 clickSignInButton() {
		signInButton.click();
		return initElements(driver, MyOpenIdPageStep2.class);
	}
}
