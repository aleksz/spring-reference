package com.gmail.at.servlet.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueProductInOrderValidator implements
		ConstraintValidator<UniqueProductInOrder, Item> {

	@Override
	public void initialize(UniqueProductInOrder constraintAnnotation) {
	}

	@Override
	public boolean isValid(Item value, ConstraintValidatorContext context) {
		Set<Item> set = new HashSet<Item>(value.getOrder().getItems());
		return set.size() == value.getOrder().getItems().size();
	}

}