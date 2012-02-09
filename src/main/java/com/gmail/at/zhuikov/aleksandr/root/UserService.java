package com.gmail.at.zhuikov.aleksandr.root;

import static org.springframework.util.StringUtils.hasText;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Component;

import com.gmail.at.zhuikov.aleksandr.root.domain.User;
import com.gmail.at.zhuikov.aleksandr.root.respository.UserRepository;

@Component
public class UserService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	private static final List<GrantedAuthority> DEFAULT_AUTHORITIES = AuthorityUtils.createAuthorityList("ROLE_USER");
	
	@Inject
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		
		String id = token.getIdentityUrl();

        User user = userRepository.find(id);
		
        if (user == null) {
        	user = new User(id, DEFAULT_AUTHORITIES);
        }

        String country = null;
        String language = null;

        List<OpenIDAttribute> attributes = token.getAttributes();

        for (OpenIDAttribute attribute : attributes) {
        	
            if (attribute.getName().equals("country")) {
            	country = attribute.getValues().get(0);
            	LOG.info("OpenId provided country [" + country + "]");
            }

            if (attribute.getName().equals("language")) {
            	language = attribute.getValues().get(0);
            	LOG.info("OpenId provided language [" + language + "]");
            }
        }

        Locale providedLocale = createLocale(language, country);
        
        if (providedLocale != null) {
        	user.setLocale(providedLocale);
        }

        userRepository.save(user);

        return user;
	}

	private Locale createLocale(String language, String country) {
		
		if (hasText(country) && hasText(language)) {
			return new Locale(language, country);
		} else if (hasText(language)) {
			return new Locale(language);
		}

		return null;
	}
}
