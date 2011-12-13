package com.gmail.at.servlet;


import java.util.Collection;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.core.style.ToStringCreator;


/**
 * An order.
 */
@Entity
@Table(name="T_ORDER")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String customer;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="ORDER_ID")
	private Collection<Item> items = new LinkedHashSet<Item>();

	/**
	 * @return the customer
	 */
	@Length(min = 5, max = 20)
	@Pattern(regexp = "[a-zA-Z]*", message = "Only characters allowed")
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the items
	 */
	public Collection<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this).append(customer).toString();
	}
}
