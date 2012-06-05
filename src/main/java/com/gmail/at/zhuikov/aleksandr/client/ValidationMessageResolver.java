package com.gmail.at.zhuikov.aleksandr.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.validation.client.AbstractValidationMessageResolver;
import com.google.gwt.validation.client.UserValidationMessagesResolver;

public class ValidationMessageResolver extends
		AbstractValidationMessageResolver implements
		UserValidationMessagesResolver {
	
	public ValidationMessageResolver() {
		super((ConstantsWithLookup) GWT.create(Translations.class));
	}
}