package com.gmail.at.zhuikov.aleksandr.root.domain.xml;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

@XmlType(name = "sort-order")
@XmlAccessorType(FIELD)
public class XmlFriendlyOrder {

	private Direction direction;
	private String property;
	
	protected XmlFriendlyOrder() {
	}
	
	public XmlFriendlyOrder(Order order) {
		this.direction = order.getDirection();
		this.property = order.getProperty();
	}

	public Direction getDirection() {
		return direction;
	}

	public String getProperty() {
		return property;
	}
}