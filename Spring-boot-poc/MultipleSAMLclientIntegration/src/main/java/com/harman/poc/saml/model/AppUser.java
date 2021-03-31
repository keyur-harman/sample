package com.harman.poc.saml.model;

import java.util.HashMap;

import org.springframework.security.core.userdetails.User;

public class AppUser {
	
	String username;
	HashMap<String, String> attributes = new HashMap<>();
	
	public AppUser(String username, HashMap<String, String> attributes) {
		this.username = username;
		this.attributes = attributes;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	
	

}
