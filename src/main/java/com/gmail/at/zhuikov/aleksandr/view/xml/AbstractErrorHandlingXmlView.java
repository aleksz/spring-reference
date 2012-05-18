package com.gmail.at.zhuikov.aleksandr.view.xml;

import static org.springframework.validation.BindingResultUtils.getBindingResult;

import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.oxm.Marshaller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyErrors;

public abstract class AbstractErrorHandlingXmlView<T> extends MarshallingView {

	protected final String commandObjectName;

	public AbstractErrorHandlingXmlView(Marshaller marshaller, String commandObjectName) {
		super(marshaller);
		this.commandObjectName = commandObjectName;
	}
	
	@Override
	protected Object locateToBeMarshalled(Map<String, Object> model)
			throws ServletException {

		BindingResult bindingResult = getBindingResult(model, commandObjectName);
		
		if (bindingResult != null && bindingResult.hasErrors()) {
			return new XmlFriendlyErrors<T>(
					(T) bindingResult.getTarget(), 
					bindingResult.getAllErrors());
		}

		return super.locateToBeMarshalled(model);
	}
	
}
