package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrdersPage extends AbstractPage {

	@FindBy(linkText = "Add new order")
	private WebElement addNewOrderLink;
	
	public OrdersPage(WebDriver driver) {
		super(driver);
	}

	public AddOrderPage clickAddNewOrder() {
		addNewOrderLink.click();
		return PageFactory.initElements(driver, AddOrderPage.class);
	}
	
	public EditOrderPage clickOrder(String customerName) {
		driver.findElement(By.partialLinkText(customerName)).click();
		return initElements(driver, EditOrderPage.class);
	}
	
	public boolean hasOrder(String customerName) {
		try {
			driver.findElement(By.partialLinkText(customerName));
		} catch (NoSuchElementException e) {
			return false;
		}
		
		return true;
	}
}
