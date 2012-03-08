package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.validation.FieldError;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonFieldError extends FieldError {

	private static final long serialVersionUID = 1130568773694185020L;

	@JsonCreator
	public JsonFieldError(
			@JsonProperty("objectName") String objectName,
			@JsonProperty("field") String field,
			@JsonProperty("rejectedValue") Object rejectedValue,
			@JsonProperty("bindingFailure") boolean bindingFailure,
			@JsonProperty("codes") String[] codes,
			@JsonProperty("arguments") Object[] arguments,
			@JsonProperty("defaultMessage") String defaultMessage) {
		super(objectName, field, rejectedValue, bindingFailure, codes, arguments, defaultMessage);
	}

}
