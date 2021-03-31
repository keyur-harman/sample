package com.harman.poc.saml.controller;

import java.util.HashMap;

import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.harman.poc.saml.config.SamlSecurityConfig;
import com.harman.poc.saml.model.AppUser;
import com.harman.poc.saml.model.MetadataModel;
import com.harman.poc.saml.service.SAMLUserService;


@RestController
public class SamlController {
	
	
	HashMap<String, ExtendedMetadataDelegate> mList = new HashMap<>();

	@Autowired
	SamlSecurityConfig samlSecurityConfig;
	
	@Autowired
	SAMLUserService samlUserService;
	
	@RequestMapping("/")
	public AppUser loadUserData() {
//		System.out.println("---->>"+samlUserService.getCurrentUserDetails().getUsername());
		return samlUserService.getCurrentUserDetails();
	}

	
	@PostMapping(value = "/addMetaDataURL")
	public String addMetaData(@RequestBody MetadataModel metaDataModel) {
		System.out.println("-----inside add metadata------"+metaDataModel.getMetaDataURL());
		try {
			if(metaDataModel.getMetaDataURL() != null) {
				ExtendedMetadataDelegate metaData = samlSecurityConfig.createExtendedMetadataDelegate(metaDataModel.getMetaDataURL());
				samlSecurityConfig.addProvider(metaData);
				mList.put(metaDataModel.getMetaDataURL(), metaData);
			}
			
		} catch (MetadataProviderException e) {
			/**
			 * Here to handle If metadata URL is invalid.
			 */
			System.out.println("Exception ---------------- " + e.getLocalizedMessage());
			e.printStackTrace();
			return "Metadata not added, Invalid URL";
		}
		System.out.println("Metadata added");
		return "Metadata added";
	}
	
	@RequestMapping(value = "/removeMetaDataURL",method=RequestMethod.POST)
	public String removeMetaData(@RequestBody MetadataModel metaDataModel) {
		System.out.println("-----inside add metadata------"+metaDataModel.getMetaDataURL());
		ExtendedMetadataDelegate metaData = mList.get(metaDataModel.getMetaDataURL());
		try {
			if (metaDataModel.getMetaDataURL() != null) {
				samlSecurityConfig.removeProvider(metaData);
				mList.remove(metaDataModel.getMetaDataURL());
			}
		} catch (MetadataProviderException e) {
			System.out.println("Exception ---------------- " + e.getLocalizedMessage());
			e.printStackTrace();
			return "Metadata not removed, Invalid URL";
		}
		System.out.println("Metadata removed");
		return "Metadata removed";
	}
	
	@GetMapping(value = "/checkMetaData")
	public String checkMetaData() {
				samlSecurityConfig.checkMetaData();
			
		return "Metadata list";
	}
	

}
