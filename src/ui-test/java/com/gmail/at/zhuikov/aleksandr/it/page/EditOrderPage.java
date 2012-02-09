package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditOrderPage extends AbstractPage {

	@FindBy(id = "customerLabel")
	private WebElement customerLabel;
	
	@FindBy(name = "email")
	private WebElement emailInput;
	
	@FindBy(name = "save")
	private WebElement saveButton;
	
	@FindBy(name = "delete")
	private WebElement deleteButton;
	
	@FindBy(id = "addItemUrl")
	private WebElement addItemLink;
	
	public EditOrderPage(WebDriver driver) {
		super(driver);
	}

	public String getCustomer() {
		return customerLabel.getText();
	}
	
	public EditOrderPage setEmail(String email) {
		emailInput.clear();
		emailInput.sendKeys(email);
		return this;
	}
	
	public String getEmail() {
		return emailInput.getAttribute("value");
	}
	
	public OrdersPage clickSaveButton() {
		saveButton.click();
		return initElements(driver, OrdersPage.class);
	}
	
	public EditOrderPage clickSaveButtonAndExpectErrors() {
		saveButton.click();
		return initElements(driver, EditOrderPage.class);
	}
	
	public OrdersPage clickDeleteButton() {
		deleteButton.click();
		return initElements(driver, OrdersPage.class);
	}
	
	public AddItemPage clickAddItemLink() {
		addItemLink.click();
		return initElements(driver, AddItemPage.class);
	}
	
	public boolean hasItem(String itemText) {
		try {
			List<WebElement> items = driver.findElements(By.className("item"));
			for (WebElement item : items) {
				if (item.getText().contains(itemText)) {
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		
		return false;
	}
	
	public boolean hasEmailErrors() {
		return hasElement(By.id("email.errors"));
	}
}
