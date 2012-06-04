package com.gmail.at.zhuikov.aleksandr.client;

import static com.google.gwt.user.client.ui.RootPanel.get;

import java.util.Set;

import javax.validation.ValidatorFactory;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;
import com.gmail.at.zhuikov.aleksandr.root.domain.OrderBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;

public class OrderEditor extends Composite implements Editor<OrderBean> {

	interface Driver extends SimpleBeanEditorDriver<OrderBean, OrderEditor> {
	}

	Driver driver = GWT.create(Driver.class);

	private Label customerErrors = Label.wrap(get("customer.errors").getElement());
	private Label emailErrors = Label.wrap(get("email.errors").getElement());
	
	EditorErrorDecorator<String> customer = new EditorErrorDecorator<String>(
			TextBox.wrap(get("customer").getElement()),
			customerErrors);
	
	EditorErrorDecorator<String> email = new EditorErrorDecorator<String>(
			TextBox.wrap(get("email").getElement()),
			emailErrors);

	Button save = SubmitButton.wrap(get("save").getElement());

	private final ValidatorFactory factory;

	public OrderEditor(ValidatorFactory factory) {
		this.factory = factory;
		
		save.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if (!validate()) {
					event.preventDefault();
					return;
				}
			}
		});
		
		edit(new OrderBean());
	}

	public void edit(OrderBean o) {
		driver.initialize(this);
		driver.edit(o);
	}

	public boolean validate() {
		
		Order order = driver.flush();
		
		driver.setConstraintViolations(
				(Set) factory.getValidator().validate(order));
		
		if (driver.hasErrors()) {
			return false;
		}
		
		return true;
	}
}
