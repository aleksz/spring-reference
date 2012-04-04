package com.gmail.at.zhuikov.aleksandr.root;

import static com.gmail.at.zhuikov.aleksandr.root.domain.GrantedAuthority.USER;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.security.openid.OpenIDAuthenticationStatus.SUCCESS;

import java.util.Collections;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.gmail.at.zhuikov.aleksandr.root.OpenIdUserService;
import com.gmail.at.zhuikov.aleksandr.root.domain.User;
import com.gmail.at.zhuikov.aleksandr.root.repository.UserRepository;

public class OpenIdUserServiceTest {

	private @Mock UserRepository userRepository;
	private @InjectMocks OpenIdUserService service = new OpenIdUserService();
	
	@Before
	public void injectMocks() {
		initMocks(this);
	}
	
	@Test
	public void setsDefaultRoleToUnknownUser() {
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(
				SUCCESS,
				"identity",
				"message",
				Collections.<OpenIDAttribute>emptyList());
		
		when(userRepository.findOne("identity")).thenReturn(null);
		
		User user = (User) service.loadUserDetails(token);
		
		assertTrue(user.getAuthorities().contains(USER));
		verify(userRepository).save(user);
	}
	
	@Test
	public void setsLocaleForUnknownUser() {
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(
				SUCCESS,
				"identity",
				"message",
				asList(countryAttribute(), languageAttribute()));
		
		when(userRepository.findOne("identity")).thenReturn(null);
		
		User user = (User) service.loadUserDetails(token);
		
		assertEquals(new Locale("est", "EE"), user.getLocale());
		verify(userRepository).save(user);
	}

	@Test
	public void usesLocaleWithLanguageWhenOpenIdDidNotProvideCountry() {
		
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(
				SUCCESS,
				"identity",
				"message",
				asList(languageAttribute()));
		
		User user = (User) service.loadUserDetails(token);
		
		assertEquals(new Locale("est"), user.getLocale());
		verify(userRepository).save(user);
	}
	
	@Test
	public void usesKnownLocaleIfOpenIdDoesNotProvideLanguage() {
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(
				SUCCESS,
				"identity",
				"message",
				asList(countryAttribute()));
		
		User knownUser = new User("identity");
		knownUser.setLocale(new Locale("est", "EE"));
		when(userRepository.findOne("identity")).thenReturn(knownUser);
		
		User user = (User) service.loadUserDetails(token);
		
		assertEquals(new Locale("est", "EE"), user.getLocale());
		verify(userRepository).save(user);
	}
	
	@Test
	public void noLocaleProvidedIfOpenIdDoesNotProvideLanguageForUnknownUser() {
		OpenIDAuthenticationToken token = new OpenIDAuthenticationToken(
				SUCCESS,
				"identity",
				"message",
				asList(countryAttribute()));
		
		when(userRepository.findOne("identity")).thenReturn(null);
		
		User user = (User) service.loadUserDetails(token);
		
		assertNull(user.getLocale());
		verify(userRepository).save(user);
	}

	private OpenIDAttribute languageAttribute() {
		return new OpenIDAttribute(
				"language",
				"type",
				asList("est"));
	}
	
	private OpenIDAttribute countryAttribute() {
		return new OpenIDAttribute(
				"country",
				"type",
				asList("EE"));
	}
}
