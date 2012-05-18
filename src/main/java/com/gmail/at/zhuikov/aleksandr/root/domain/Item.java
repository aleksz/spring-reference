package com.gmail.at.zhuikov.aleksandr.root.domain;


import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.style.ToStringCreator;


@Entity
@XmlRootElement
@XmlAccessorType(FIELD)
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@XmlTransient
	private Order order;

	private String product;

	private double price;

	private int quantity;

	protected Item() {
	}
	
	public Item(Order order, String product, double price) {
		this.order = order;
		this.product = product;
		this.price = price;
		order.getItems().add(this);
	}

	@Valid
	public Order getOrder() {
		return order;
	}

	@NotBlank
	public String getProduct() {
		return product;
	}

	@Min(0)
	public double getPrice() {
		return price;
	}

	@Min(1)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("product", product)
				.append("quantity", quantity)
				.append("price", price)
				.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		if (obj.getClass() != getClass()) {
			return false;
		}

		Item other = (Item) obj;

		return new EqualsBuilder()
				.append(product, other.product)
				.append(order, other.order)
				.append(price, other.price)
				.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(product)
				.append(order)
				.append(price)
				.toHashCode();
	}
}