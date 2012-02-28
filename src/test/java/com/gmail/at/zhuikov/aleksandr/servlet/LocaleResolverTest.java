package com.gmail.at.zhuikov.aleksandr.servlet;

import static com.gmail.at.zhuikov.aleksandr.root.domain.GrantedAuthority.ADMIN;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;

public class LocaleResolverTest {

	private @Mock Authentication principal;
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private LocaleResolver resolver = new LocaleResolver();
	
	@Before
	public void injectMocks() {
		initMocks(this);
	}
	
	@Test
	public void supportsLocalesWithCountry() {
		request.addPreferredLocale(new Locale("ru", "RU"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
	@Test
	public void selectsLocaleFromPrincipal() {
		User user = new User("username");
		user.setLocale(new Locale("ru"));
		request.setUserPrincipal(principal);
		when(principal.getPrincipal()).thenReturn(user);
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
	@Test
	public void selectsRequestLocaleIfPrincipalDoesNotHaveLocale() {
		User user = new User("username");
		request.setUserPrincipal(principal);
		when(principal.getPrincipal()).thenReturn(user);
		request.addPreferredLocale(new Locale("ru"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
	@Test
	public void selectsRequestLocaleIfPrincipalIsNotAwareOfLocale() {
		UserDetails user = new org.springframework.security.core.userdetails.User(
				"username", "", asList(ADMIN));
		request.setUserPrincipal(principal);
		when(principal.getPrincipal()).thenReturn(user);
		request.addPreferredLocale(new Locale("ru"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
	@Test
	public void selectsRequestLocaleIfPrincipalHasUnsupportedLocale() {
		User user = new User("username");
		user.setLocale(new Locale("jp"));
		request.setUserPrincipal(principal);
		when(principal.getPrincipal()).thenReturn(user);
		request.addPreferredLocale(new Locale("ru"));
		assertEquals("ru", resolver.resolveLocale(request).getLanguage());
	}
	
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
