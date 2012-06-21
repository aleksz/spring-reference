package com.gmail.at.zhuikov.aleksandr.view.xml;

import javax.inject.Inject;

import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Component("order")
public class OrderXmlView extends AbstractErrorHandlingXmlView<Order> {

	public OrderXmlView() {
		super("order");
	}

	@Inject
	@Override
	public void setMarshaller(Marshaller marshaller) {
		super.setMarshaller(marshaller);
	}
}
