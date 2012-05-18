package com.gmail.at.zhuikov.aleksandr.root.domain.xml;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(FIELD)
public class XmlFriendlyError {
	
	private String objectName;
	private String field;
	private String defaultMessage;
	private Object rejectedValue;
	private String[] codes;
	@XmlTransient
	private Object[] arguments;
	
	protected XmlFriendlyError() {}
	
	public XmlFriendlyError(
			String objectName, 
			String field, 
			String defaultMessage, 
			Object rejectedValue,
			String[] codes,
			Object[] arguments) {
		
		this.objectName = objectName;
		this.field = field;
		this.defaultMessage = defaultMessage;
		this.rejectedValue = rejectedValue;
		this.codes = codes;
		this.arguments = arguments;
	}
	
	public String getObjectName() {
		return objectName;
	}
	
	public String getField() {
		return field;
	}
	
	public String getDefaultMessage() {
		return defaultMessage;
	}
	
	public Object getRejectedValue() {
		return rejectedValue;
	}
	
	public String[] getCodes() {
		return codes;
	}
	
	public Object[] getArguments() {
		return arguments;
	}
}
