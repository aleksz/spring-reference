package com.gmail.at.zhuikov.aleksandr.view.xml;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;

import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyPage;

public abstract class AbstractPageXmlView<T> extends MarshallingView {

	@Override
	protected Object locateToBeMarshalled(Map<String, Object> model)
			throws ServletException {
		
		Object result = super.locateToBeMarshalled(model);
		
		if (result != null) {
			return result;
		}
		
		return new XmlFriendlyPage<T>((Page<T>) model.get("page"));
	}

	@Inject
	@Override
	public void setMarshaller(Marshaller marshaller) {
		super.setMarshaller(marshaller);
	}
}
