package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrdersPage extends AbstractPage {

	@FindBy(id = "addNewOrder")
	private WebElement addNewOrderLink;
	
	public OrdersPage(WebDriver driver) {
		super(driver);
	}

	public AddOrderPage clickAddNewOrder() {
		addNewOrderLink.click();
		return initElements(driver, AddOrderPage.class);
	}
	
	public String getAddNewOrderLinkText() {
		return addNewOrderLink.getText();
	}
	
	public EditOrderPage clickOrder(String customerName) {
		driver.findElement(By.partialLinkText(customerName)).click();
		return initElements(driver, EditOrderPage.class);
	}
	
	public String getOrderLinkText(String customerName) {
		return driver.findElement(By.partialLinkText(customerName)).getText();
	}
	
	public boolean hasOrder(String customerName) {
		return hasElement(By.partialLinkText(customerName));
	}
	
	public boolean hasPages() {
		return hasElement(By.id("pages"));
	}
}
