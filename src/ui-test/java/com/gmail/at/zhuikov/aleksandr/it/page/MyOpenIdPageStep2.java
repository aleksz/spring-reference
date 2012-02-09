package com.gmail.at.zhuikov.aleksandr.it.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class MyOpenIdPageStep2 extends AbstractPage {

	@FindBy(id = "continue-button")
	private WebElement continueButton;
	
	@FindBy(name = "persona_id")
	private WebElement personaDropdown;
	
	@FindBy(name = "skip_next_time")
	private WebElement skipNextTimeCheckbox;
	
	public MyOpenIdPageStep2(WebDriver driver) {
		super(driver);
	}

	public void clickContinueButton() {
		continueButton.click();
	}
	
	public MyOpenIdPageStep2 uncheckSkipNextTimeCheckbox() {
		skipNextTimeCheckbox.click();
		return this;
	}
	
	public MyOpenIdPageStep2 selectPersona(String persona) {
		new Select(personaDropdown).selectByVisibleText(persona);
		return this;
	}
}
