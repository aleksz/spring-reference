package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddOrderPage extends AbstractPage {

	@FindBy(name = "customer")
	private WebElement customerInput;
	
	@FindBy(name = "email")
	private WebElement emailInput;
	
	@FindBy(name = "save")
	private WebElement saveButton;
	
	public AddOrderPage(WebDriver driver) {
		super(driver);
	}

	public AddOrderPage setCustomer(String customer) {
		customerInput.clear();
		customerInput.sendKeys(customer);
		return this;
	}
	
	public AddOrderPage setEmail(String email) {
		emailInput.clear();
		emailInput.sendKeys(email);
		return this;
	}
	
	public OrdersPage clickSaveButton() {
		saveButton.click();
		return initElements(driver, OrdersPage.class);
	}
	
	public AddOrderPage clickSaveButtonAndExpectErrors() {
		saveButton.click();
		return initElements(driver, AddOrderPage.class);
	}
	
	public boolean hasCustomerErrors() {
		return hasElement(By.id("customer.errors"));
	}
	
	public boolean hasEmailErrors() {
		return hasElement(By.id("email.errors"));
	}
}
