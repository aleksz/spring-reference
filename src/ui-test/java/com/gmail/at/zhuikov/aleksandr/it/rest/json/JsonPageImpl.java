package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPageImpl<T> extends PageImpl<T> {

	private static final long serialVersionUID = -5042582604225972832L;

	@JsonCreator
	public JsonPageImpl(
			@JsonProperty("content") List<T> content,
			@JsonProperty("number") int page,
			@JsonProperty("size") int size,
			@JsonProperty("totalPages") int total,
			@JsonProperty("sort") JsonOrder[] orsers) {
		
		super(content == null ? new ArrayList<T>() : content,
				new PageRequest(page, size, new Sort(orsers)), total);
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class JsonOrder extends Order {
		
		@JsonCreator
		public JsonOrder(
				@JsonProperty("direction") Direction direction,
				@JsonProperty("property") String property) {
			
			super(direction, property);
		}
	}
}
