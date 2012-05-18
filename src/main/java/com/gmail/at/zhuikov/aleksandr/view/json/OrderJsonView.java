package com.gmail.at.zhuikov.aleksandr.view.json;

import org.springframework.stereotype.Component;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

@Component("order")
public class OrderJsonView extends AbstractErrorHandlingJsonView<Order> {

	public OrderJsonView() {
		super("order");
	}

}
