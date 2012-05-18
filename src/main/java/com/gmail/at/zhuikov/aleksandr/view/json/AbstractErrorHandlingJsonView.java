package com.gmail.at.zhuikov.aleksandr.view.json;

import static org.springframework.validation.BindingResultUtils.getBindingResult;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyErrors;

public class AbstractErrorHandlingJsonView<T> extends MappingJackson2JsonView {

	private final String commandName;

	public AbstractErrorHandlingJsonView(String commandName) {
		this.commandName = commandName;
		setModelKey(commandName);
		setExtractValueFromSingleKeyModel(true);
	}
	
	@Override
	protected Object filterModel(Map<String, Object> model) {
		
		BindingResult bindingResult = getBindingResult(model, commandName);
		
		if (bindingResult != null && bindingResult.hasErrors()) {
			return new XmlFriendlyErrors<T>(
					(T) bindingResult.getTarget(), 
					bindingResult.getAllErrors());
		}

		return super.filterModel(model);
	}

	@Autowired
	@Override
	public void setObjectMapper(ObjectMapper objectMapper) {
		super.setObjectMapper(objectMapper);
	}
}
