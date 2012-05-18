package com.gmail.at.zhuikov.aleksandr.servlet;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

@Component("jacksonObjectMapper")
public class JacksonObjectMapperFactory extends AbstractFactoryBean<ObjectMapper> {

	@Override
	protected ObjectMapper createInstance() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(
				new JaxbAnnotationIntrospector());
		mapper.setAnnotationIntrospector(
				new JaxbAnnotationIntrospector());
		mapper.enableDefaultTyping(DefaultTyping.JAVA_LANG_OBJECT, As.PROPERTY);
		return mapper;
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

}
