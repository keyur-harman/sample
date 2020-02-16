package com.harman.clinical6.poc.springsecurity.config;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

/**
 * This class implements AccessDecisionManager responsible for making final access control decisions. 
 * @author RChoudhury2
 *
 */

public class MyAccessDecisionManager implements AccessDecisionManager {
	/**
	 * The AccessDecisionManager's decide method 
	 * is passed with all the relevant information it needs in order to make an authorization decision
	 */
@Override
public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes){
    if(configAttributes == null){
        return  ;
    }
    Iterator<ConfigAttribute> ite = configAttributes.iterator();
    while(ite.hasNext()){

        ConfigAttribute ca = ite.next();
        String needRole = ((SecurityConfig)ca).getAttribute();
        for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
            if(needRole.trim().equals(grantedAuthority.getAuthority().trim())){
                return;
            }
        }
    }
    throw new AccessDeniedException("Access is denied to this user");
}

@Override
public boolean supports(ConfigAttribute attribute) {
	return true;
}

@Override
public boolean supports(Class<?> clazz) {
	return FilterInvocation.class.isAssignableFrom(clazz);
}
}
