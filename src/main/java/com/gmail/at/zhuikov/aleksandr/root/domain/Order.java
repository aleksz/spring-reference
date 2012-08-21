package com.gmail.at.zhuikov.aleksandr.root.domain;


import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "T_ORDER")
@GroupSequence({ Order.class, ComplexValidation.class })
@XmlRootElement
@XmlType(name = "order-entity")
@XmlAccessorType(FIELD)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	protected String customer;
	
	protected String email;
	
	protected Date date = new Date();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private Collection<Item> items = new LinkedHashSet<Item>();

	protected Order() {
	}

	public Order(String customer) {
		this.customer = customer;
	}
	
	@NotBlank
	@Size(min = 5, max = 30)
	@Pattern(regexp = "[a-zA-Z\\s]*", message = "{onlyCharsAllowed}")
	public String getCustomer() {
		return customer;
	}

	@Valid
	@UniqueProductInOrder(groups = ComplexValidation.class)
	public Collection<Item> getItems() {
		return items;
	}

	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}
	
	@NotEmpty
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}
	
//	@Override
//	public String toString() {
//		return new com.google.inject.internal.ToStringBuilder(getClass()).add("customer", customer).toString();
//		return new ToStringBuilder(this).append(customer).toString();
//		return customer;
//	}
	
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

		Order other = (Order) obj;

		return new com.flipthebird.gwthashcodeequals.EqualsBuilder()
				.append(customer, other.customer)
				.append(date, other.date)
				.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new com.flipthebird.gwthashcodeequals.HashCodeBuilder()
				.append(customer)
				.append(date)
				.toHashCode();
	}
}

interface ComplexValidation {}