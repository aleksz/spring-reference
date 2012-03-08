package com.gmail.at.zhuikov.aleksandr.it.rest.json;

import static java.util.Arrays.asList;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

	@JsonDeserialize(contentAs = JsonFieldError.class)
	private List<ObjectError> errors;
	
	protected ErrorResponse() {
	}
	
	public ErrorResponse(ObjectError... errors) {
		this.errors = asList(errors);
		this.errors.add(new FieldError(null, null, null));
	}

	public List<ObjectError> getErrors() {
		return errors;
	}
}
