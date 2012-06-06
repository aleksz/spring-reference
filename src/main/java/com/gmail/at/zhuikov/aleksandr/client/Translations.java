package com.gmail.at.zhuikov.aleksandr.client;


public interface Translations extends
		org.hibernate.validator.ValidationMessages {

	@DefaultStringValue("Only characters allowed")
	@Key("onlyCharsAllowed")
	String onlyCharsAllowed();
}