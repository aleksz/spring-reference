package com.gmail.at.zhuikov.aleksandr.root.domain.xml;

import static org.springframework.util.StringUtils.hasText;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ObjectErrorAdapter extends XmlAdapter<XmlFriendlyError, ObjectError> {

	@Override
	public ObjectError unmarshal(XmlFriendlyError objectError)
			throws Exception {
		
		if (hasText(objectError.getField())) {
			
			return new FieldError(
					objectError.getObjectName(),
					objectError.getField(),
					objectError.getRejectedValue(),
					true,
					objectError.getCodes(),
					objectError.getArguments(),
					objectError.getDefaultMessage());
		}
		
		return new ObjectError(objectError.getObjectName(), objectError.getDefaultMessage());
	}

	@Override
	public XmlFriendlyError marshal(ObjectError objectError) throws Exception {
		
		String field = null;
		Object rejectedValue = null;
		
		if (objectError instanceof FieldError) {
			FieldError fieldError = (FieldError) objectError;
			field = fieldError.getField();
			rejectedValue = fieldError.getRejectedValue();
		}
		
		return new XmlFriendlyError(
				objectError.getObjectName(), 
				field, 
				objectError.getDefaultMessage(),
				rejectedValue,
				objectError.getCodes(),
				objectError.getArguments());
	}
	
}
