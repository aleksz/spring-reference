package com.gmail.at.zhuikov.aleksandr.servlet;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class LocaleResolverTest {

	private MockHttpServletRequest request = new MockHttpServletRequest();
	private LocaleResolver resolver = new LocaleResolver();
	
	@Test
	public void selectsSupportedLocale() {
		request.addPreferredLocale(new Locale("ru"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}

	@Test
	public void selectsFirstSupportedLocale() {
		request.addPreferredLocale(new Locale("en"));
		request.addPreferredLocale(new Locale("ru"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
	@Test
	public void skipsUnsupportedLocale() {
		request.addPreferredLocale(new Locale("ru"));
		request.addPreferredLocale(new Locale("jp"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
	@Test
	public void selectsDefaultLocaleIfRequestedLocaleNotSupported() {
		request.addPreferredLocale(new Locale("jp"));
		assertEquals("en", resolver.resolveLocale(request).getLanguage());
	}
}
