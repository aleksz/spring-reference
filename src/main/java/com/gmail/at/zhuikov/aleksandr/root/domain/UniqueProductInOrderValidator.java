package com.gmail.at.zhuikov.aleksandr.root.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueProductInOrderValidator implements
		ConstraintValidator<UniqueProductInOrder, Collection<Item>> {

	@Override
	public void initialize(UniqueProductInOrder constraintAnnotation) {
	}

	@Override
	public boolean isValid(Collection<Item> items, ConstraintValidatorContext context) {
		Set<Item> set = new HashSet<Item>(items);
		return set.size() == items.size();
	}

}