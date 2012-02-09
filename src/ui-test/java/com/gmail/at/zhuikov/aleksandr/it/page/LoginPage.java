package com.gmail.at.zhuikov.aleksandr.it.page;

import static org.openqa.selenium.support.PageFactory.initElements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

	@FindBy(name = "openid_identifier")
	private WebElement openIdIdentifierInput;
	
	@FindBy(name = "openid_username")
	private WebElement openIdUsernameInput;
	
	@FindBy(name = "login")
	private WebElement loginButton;
	
	@FindBy(id = "google")
	private WebElement googleButton;
	
	@FindBy(id = "myOpenId")
	private WebElement myOpenIdButton;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public LoginPage setOpenIdIdentifier(String identifier) {
		openIdIdentifierInput.clear();
		openIdIdentifierInput.sendKeys(identifier);
		return this;
	}
	
	public LoginPage setOpenIdUsername(String username) {
		openIdUsernameInput.clear();
		openIdUsernameInput.sendKeys(username);
		return this;
	}
	
	public String getLoginButtonText() {
		return loginButton.getAttribute("value");
	}
	
	public void clickLoginButton() {
		loginButton.click();
	}
	
	public void clickGoogleButton() {
		googleButton.click();
	}
	
	public LoginPage clickMyOpenIdButton() {
		myOpenIdButton.click();
		return this;
	}
	
	public void loginWithMyOpenId(String username, String password) {
		clickMyOpenIdButton();
		setOpenIdUsername(username);
		clickLoginButton();
		initElements(driver, MyOpenIdPage.class)
				.setPassword(password)
				.clickSignInButton()
				.uncheckSkipNextTimeCheckbox()
				.clickContinueButton();
	}
	
	public void loginWithMyOpenId(String username, String password, String persona) {
		clickMyOpenIdButton();
		setOpenIdUsername(username);
		clickLoginButton();
		initElements(driver, MyOpenIdPage.class)
				.setPassword(password)
				.clickSignInButton()
				.selectPersona(persona)
				.uncheckSkipNextTimeCheckbox()
				.clickContinueButton();
	}
}
