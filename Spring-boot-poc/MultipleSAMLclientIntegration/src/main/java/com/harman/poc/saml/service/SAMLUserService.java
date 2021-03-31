package com.harman.poc.saml.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

import com.harman.poc.saml.model.AppUser;


@Service
public class SAMLUserService implements SAMLUserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SAMLUserService.class);
	private static AppUser currentUser;

	public String loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
		/**
		 * Anonymus role added to handle unauthorized users trying to login AND Spring
		 * security needs role in ROLE_* format
		 */
		 String user = credential.getNameID().getValue();
		LOGGER.info("--------- Loading UserDetails by SAMLCredentials: {}", credential.getNameID().getValue());
		System.out.println("------------------------------ load userBy SAML----------------");
		HashMap<String, String> attributeMap = new HashMap<>();
		for (int i = 0; i < credential.getAttributes().size(); i++) {
			System.out.println(credential.getAttributes().get(i).getName());
			String attributeName = credential.getAttributes().get(i).getName();
			String attributeValue = credential.getAttributeAsString(attributeName);
			System.out.println(attributeValue);
			attributeMap.put(attributeName, attributeValue);
		}
		
		setCurrentUser(new AppUser(user, attributeMap));
		return user;
	}
	
	public void setCurrentUser(AppUser appUser) {
		currentUser = appUser;
	}
	
	public AppUser getCurrentUserDetails() {
		return currentUser;
	}

}
