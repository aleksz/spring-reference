package com.gmail.at.zhuikov.aleksandr.root.domain.xml;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.validation.ObjectError;


@XmlRootElement
@XmlAccessorType(FIELD)
public class XmlFriendlyErrors<T> {

	private T target;
	
	@XmlJavaTypeAdapter(ObjectErrorAdapter.class)
	private List<ObjectError> errors;
	
	protected XmlFriendlyErrors() {}
	
	public XmlFriendlyErrors(T target, List<ObjectError> errors) {
		this.target = target;
		this.errors = errors;
	}
	
	public T getTarget() {
		return target;
	}
	
	public List<ObjectError> getErrors() {
		return errors;
	}
}
