package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.validation.ObjectError;


public class JsonObjectError extends ObjectError {

	private static final long serialVersionUID = 1130568773694185020L;

	@JsonCreator
	public JsonObjectError(
			@JsonProperty("objectName") String objectName,
			@JsonProperty("codes") String[] codes,
			@JsonProperty("arguments") Object[] arguments,
			@JsonProperty("defaultMessage") String defaultMessage) {
		super(objectName, codes, arguments, defaultMessage);
	}

}
