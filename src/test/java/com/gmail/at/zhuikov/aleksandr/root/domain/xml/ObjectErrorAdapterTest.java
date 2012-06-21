package com.gmail.at.zhuikov.aleksandr.root.domain.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ObjectErrorAdapterTest {

	private ObjectErrorAdapter adapter = new ObjectErrorAdapter();
	
	@Test
	public void marshalObjectError() throws Exception {
		XmlFriendlyError result = adapter.marshal(new ObjectError("order",
				"msg"));
		
		assertEquals("order", result.getObjectName());
		assertNull(result.getField());
		assertEquals("msg", result.getDefaultMessage());
		assertNull(result.getRejectedValue());
		assertNull(result.getCodes());
		assertNull(result.getArguments());
	}

	@Test
	public void marshalFieldError() throws Exception {
		XmlFriendlyError result = adapter.marshal(new FieldError("order",
				"email", "msg"));

		assertEquals("order", result.getObjectName());
		assertEquals("email", result.getField());
		assertEquals("msg", result.getDefaultMessage());
		assertNull(result.getRejectedValue());
		assertNull(result.getCodes());
		assertNull(result.getArguments());
	}

	@Test
	public void unmarshalObjectError() throws Exception {
		
		ObjectError result = adapter
				.unmarshal(new XmlFriendlyError("order", null,
						"msg", null, null, null));

		assertEquals("order", result.getObjectName());
		assertEquals("msg", result.getDefaultMessage());
		assertNull(result.getCodes());
		assertNull(result.getArguments());
	}
	
	@Test
	public void unmarshalFieldError() throws Exception {
		
		FieldError result = (FieldError) adapter
				.unmarshal(new XmlFriendlyError("order", "email",
						"msg", "val", null, null));

		assertEquals("order", result.getObjectName());
		assertEquals("email", result.getField());
		assertEquals("msg", result.getDefaultMessage());
		assertEquals("val", result.getRejectedValue());
		assertNull(result.getCodes());
		assertNull(result.getArguments());
	}
}
