package com.gmail.at.servlet;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.core.style.ToStringCreator;
import org.springframework.core.style.ToStringStyler;

/**
 * An item in an order
 */
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Order order;

	private String product;

	private double price;

	private int quantity;

	protected Item() {
	}
	
	public Item(Order order) {
		this.order = order;
	}
	
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the id
	 */
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
}
