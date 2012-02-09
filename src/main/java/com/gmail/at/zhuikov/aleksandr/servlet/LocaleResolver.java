package com.gmail.at.zhuikov.aleksandr.servlet;

import static java.util.Locale.ENGLISH;

import java.security.Principal;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;

@Component("localeResolver")
public class LocaleResolver extends AbstractLocaleResolver {

	private Set<String> supportedLocales = new HashSet<String>();

	{
		for (Locale l : Locale.getAvailableLocales()) {

			String name = "/translations";
			name += "_" + l + ".properties";

			if (getClass().getResourceAsStream(name) != null) {
				supportedLocales.add(l.getLanguage());
			}
		}

		setDefaultLocale(ENGLISH);
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {

	    Principal userPrincipal = request.getUserPrincipal();

	    if (userPrincipal != null) {
	    	User user = (User) ((Authentication) userPrincipal).getPrincipal();
		
		    if (user.getLocale() != null && isSupported(user.getLocale())) {
		    	return user.getLocale();
		    }
	    }
	    
		Enumeration<Locale> locales = request.getLocales();

		while (locales.hasMoreElements()) {
			Locale l = locales.nextElement();
			if (isSupported(l)) {
				return l;
			}
		}

		return getDefaultLocale();
	}

	private boolean isSupported(Locale locale) {
		return supportedLocales.contains(locale.getLanguage());
	}
	
	@Override
	public void setLocale(HttpServletRequest request,
			HttpServletResponse response, Locale locale) {
		throw new UnsupportedOperationException(
				"Cannot change HTTP accept header - use a different locale resolution strategy");
	}
}
