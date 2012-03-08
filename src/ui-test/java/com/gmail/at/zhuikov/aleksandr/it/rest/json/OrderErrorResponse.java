package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderErrorResponse extends ErrorResponse  {

	private Order order;
	
	protected OrderErrorResponse() {
	}
	
	public OrderErrorResponse(JsonFieldError[] errors, Order order) {
		super(errors);
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}
}
