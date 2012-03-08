package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.core.style.ToStringCreator;
import org.springframework.data.domain.Page;

import com.gmail.at.zhuikov.aleksandr.root.domain.Order;

public class ListOrdersResult {

	private Page<Order> page;
	
	@JsonCreator
	public ListOrdersResult(@JsonProperty("page") JsonPageImpl<Order> page) {
		this.page = page;
	}
	
	public Page<Order> getPage() {
		return page;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this).append(page).toString();
	}
}
