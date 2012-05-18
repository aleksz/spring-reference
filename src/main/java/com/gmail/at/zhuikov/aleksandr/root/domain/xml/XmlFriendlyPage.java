package com.gmail.at.zhuikov.aleksandr.root.domain.xml;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;


@XmlRootElement
@XmlAccessorType(FIELD)
public class XmlFriendlyPage <T> implements Page<T> {

	private List<T> content = new ArrayList<T>();
	private int pageNumber;
	private int pageSize;
	
	private List<XmlFriendlyOrder> orders = new ArrayList<XmlFriendlyOrder>();
	
	private long total;
	
	protected XmlFriendlyPage() {
	}
	
	public XmlFriendlyPage(Page<T> page) {
		this.content = page.getContent();
		pageNumber = page.getNumber();
		pageSize = page.getSize();
		this.total = page.getTotalElements();
		
		if (page.getSort() != null) {
			for (Order order : page.getSort()) {
				orders.add(new XmlFriendlyOrder(order));
			}
		}
	}
	
	@Override
	public int getNumber() {
		return pageNumber;
	}

	@Override
	public int getSize() {
		return pageSize;
	}

	@Override
	public int getTotalPages() {
		return getSize() == 0 ? 0 : (int) Math.ceil((double) total / (double) getSize());
	}

	@Override
	public int getNumberOfElements() {
		return content.size();
	}

	@Override
	public long getTotalElements() {
		return total;
	}

	@Override
	public boolean hasPreviousPage() {
		return getNumber() > 0;
	}

	@Override
	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	@Override
	public boolean hasNextPage() {
		return ((getNumber() + 1) * getSize()) < total;
	}

	@Override
	public boolean isLastPage() {
		return !hasNextPage();
	}

	@Override
	public Iterator<T> iterator() {
		return content.iterator();
	}

	@Override
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	@Override
	public boolean hasContent() {
		return !content.isEmpty();
	}

	@Override
	public Sort getSort() {
		List<Order> springOrders = new ArrayList<Sort.Order>();
		for (XmlFriendlyOrder order : orders) {
			springOrders.add(new Order(order.getDirection(), order.getProperty()));
		}
		return new Sort(springOrders);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(total)
			.append(content)
			.append(pageNumber)
			.append(pageSize)
			.append(orders)
			.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PageImpl<?>)) {
			return false;
		}

		XmlFriendlyPage<?> that = (XmlFriendlyPage<?>) obj;
		
		return new EqualsBuilder()
				.append(total, that.total)
				.append(content, that.content)
				.append(pageNumber, that.pageNumber)
				.append(pageSize, that.pageSize)
				.append(orders, that.orders)
				.isEquals();
	}
}
