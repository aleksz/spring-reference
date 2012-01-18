package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddItemPage extends AbstractPage {

	@FindBy(name = "product")
	private WebElement productInput;
	
	@FindBy(name = "price")
	private WebElement priceInput;
	
	@FindBy(name = "quantity")
	private WebElement quantityInput;
	
	@FindBy(name = "save")
	private WebElement saveButton;
	
	public AddItemPage(WebDriver driver) {
		super(driver);
	}

	public AddItemPage setProduct(String product) {
		productInput.clear();
		productInput.sendKeys(product);
		return this;
	}
	
	public AddItemPage setPrice(String price) {
		priceInput.clear();
		priceInput.sendKeys(price);
		return this;
	}
	
	public AddItemPage setQuantity(String quantity) {
		quantityInput.clear();
		quantityInput.sendKeys(quantity);
		return this;
	}
	
	public EditOrderPage clickSaveButton() {
		saveButton.click();
		return initElements(driver, EditOrderPage.class);
	}
	
	public AddItemPage clickSaveButtonAndExpectErrors() {
		saveButton.click();
		return initElements(driver, AddItemPage.class);
	}
	
	public boolean hasProductErrors() {
		return hasElement(By.id("product.errors"));
	}
	
	public boolean hasPriceErrors() {
		return hasElement(By.id("price.errors"));
	}
	
	public boolean hasQuantityErrors() {
		return hasElement(By.id("quantity.errors"));
	}
	
	public boolean hasOrderErrors() {
		return hasElement(By.id("order.*.errors"));
	}
}
