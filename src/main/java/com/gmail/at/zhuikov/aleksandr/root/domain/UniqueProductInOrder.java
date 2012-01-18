package com.gmail.at.zhuikov.aleksandr.root.domain;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(METHOD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueProductInOrderValidator.class)
@Documented
public @interface UniqueProductInOrder {

    String message() default "Order can contain only one item with same name and price";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
