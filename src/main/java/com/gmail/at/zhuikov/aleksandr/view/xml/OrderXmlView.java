package com.gmail.at.zhuikov.aleksandr.view.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Component("order")
public class OrderXmlView extends AbstractErrorHandlingXmlView<Order> {

	@Autowired
	public OrderXmlView(Marshaller marshaller) {
		super(marshaller, "order");
	}

}
