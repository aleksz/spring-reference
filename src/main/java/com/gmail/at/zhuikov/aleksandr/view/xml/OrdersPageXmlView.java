package com.gmail.at.zhuikov.aleksandr.view.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.stereotype.Component;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Component("orders")
public class OrdersPageXmlView extends AbstractPageXmlView<Order> {

	@Autowired
	public OrdersPageXmlView(Marshaller marshaller) {
		super(marshaller);
	}
	
}
