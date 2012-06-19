package com.gmail.at.zhuikov.aleksandr.rest.xml;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.HttpStatusCodeException;

import com.gmail.at.zhuikov.aleksandr.root.domain.xml.XmlFriendlyErrors;

public class MyResponseErrorHandler extends DefaultResponseErrorHandler {

	private HttpMessageConverterExtractor<XmlFriendlyErrors> messageExtractor;
	
	public MyResponseErrorHandler(List<HttpMessageConverter<?>> messageConverters) {
		messageExtractor = new HttpMessageConverterExtractor<XmlFriendlyErrors>(XmlFriendlyErrors.class, messageConverters);
	}
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		XmlFriendlyErrors<?> errorBody = messageExtractor.extractData(response);
		throw new MyHttpStatusCodeException(response.getStatusCode(), errorBody);
	}

	public static class MyHttpStatusCodeException extends HttpStatusCodeException {

		private static final long serialVersionUID = 1L;
		
		private final XmlFriendlyErrors<?> errorBody;

		protected MyHttpStatusCodeException(HttpStatus statusCode, XmlFriendlyErrors<?> errorBody) {
			super(statusCode);
			this.errorBody = errorBody;
		}
		
		public XmlFriendlyErrors<?> getErrorBody() {
			return errorBody;
		}
	}
}
