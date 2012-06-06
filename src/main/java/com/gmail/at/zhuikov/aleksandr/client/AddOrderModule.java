package com.gmail.at.zhuikov.aleksandr.client;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import com.google.gwt.core.client.EntryPoint;

public class AddOrderModule implements EntryPoint {

	public void onModuleLoad() {

		ValidatorFactory factory = Validation.byDefaultProvider().configure()
				.buildValidatorFactory();

		new OrderEditor(factory);
	}
}
