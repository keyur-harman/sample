package com.harman.poc.saml.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLDiscovery;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.SAMLLogoutFilter;
import org.springframework.security.saml.SAMLLogoutProcessingFilter;
import org.springframework.security.saml.SAMLProcessingFilter;
import org.springframework.security.saml.SAMLWebSSOHoKProcessingFilter;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.MetadataDisplayFilter;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements DisposableBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Value("${saml.sp}")
	private String samlAudience;
	
	@Value("${saml.entityUrl}")
	private String entityUrl;

	@Autowired
	@Qualifier("saml")
	private SavedRequestAwareAuthenticationSuccessHandler samlAuthSuccessHandler;

	@Autowired
	@Qualifier("saml")
	private SimpleUrlAuthenticationFailureHandler samlAuthFailureHandler;

	@Autowired
	@Qualifier("saml")
	private Timer samlBackgroundTaskTimer;

	@Autowired
	@Qualifier("saml")
	private MultiThreadedHttpConnectionManager samlMultiThreadedHttpConnectionManager;

	@Autowired
	private SAMLEntryPoint samlEntryPoint;

	@Autowired
	private SAMLLogoutFilter samlLogoutFilter;

	@Autowired
	private MetadataDisplayFilter metadataDisplayFilter;

	@Autowired
	private SAMLLogoutProcessingFilter samlLogoutProcessingFilter;

//	@Autowired
//	private SAMLDiscovery samlDiscovery;

	@Autowired
	private SAMLAuthenticationProvider samlAuthenticationProvider;

	@Autowired
	private ExtendedMetadata extendedMetadata;

	@Autowired
	private KeyManager keyManager;

	public MetadataGenerator metadataGenerator() {
		MetadataGenerator metadataGenerator = new MetadataGenerator();
		metadataGenerator.setEntityId(samlAudience);
		metadataGenerator.setExtendedMetadata(extendedMetadata);
		metadataGenerator.setIncludeDiscoveryExtension(false);
		metadataGenerator.setKeyManager(keyManager);
		metadataGenerator.setEntityBaseURL(entityUrl);
		return metadataGenerator;
	}

	@Bean
	public SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter() throws Exception {
		SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter = new SAMLWebSSOHoKProcessingFilter();
		samlWebSSOHoKProcessingFilter.setAuthenticationSuccessHandler(samlAuthSuccessHandler);
		samlWebSSOHoKProcessingFilter.setAuthenticationManager(authenticationManager());
		samlWebSSOHoKProcessingFilter.setAuthenticationFailureHandler(samlAuthFailureHandler);
		return samlWebSSOHoKProcessingFilter;
	}

	/**
	 * Processing filter for WebSSO profile messages
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SAMLProcessingFilter samlWebSSOProcessingFilter() throws Exception {
		SAMLProcessingFilter samlWebSSOProcessingFilter = new SAMLProcessingFilter();
		samlWebSSOProcessingFilter.setAuthenticationManager(authenticationManager());
		samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(samlAuthSuccessHandler);
		samlWebSSOProcessingFilter.setAuthenticationFailureHandler(samlAuthFailureHandler);
		return samlWebSSOProcessingFilter;
	}

	/**
	 * Define the security filter chain in order to support SSO Auth by using SAML
	 * 2.0
	 *
	 * @return Filter chain proxy
	 */
	@Bean
	public FilterChainProxy samlFilter() throws Exception {
		List<SecurityFilterChain> chains = new ArrayList<>();
		chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/login/**"), samlEntryPoint));
		chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/logout/**"), samlLogoutFilter));
		chains.add(
				new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/metadata/**"), metadataDisplayFilter));
		chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSO/**"),
				samlWebSSOProcessingFilter()));
		chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSOHoK/**"),
				samlWebSSOHoKProcessingFilter()));
		chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SingleLogout/**"),
				samlLogoutProcessingFilter));
//		chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/discovery/**"), samlDiscovery));
		return new FilterChainProxy(chains);
	}

	/**
	 * Returns the authentication manager currently used by Spring. It represents a
	 * bean definition with the aim allow wiring from other classes performing the
	 * Inversion of Control (IoC).
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public MetadataGeneratorFilter metadataGeneratorFilter() {
		return new MetadataGeneratorFilter(metadataGenerator());
	}

	/**
	 * Defines the web based security configuration.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable();
		http.httpBasic().authenticationEntryPoint((request, response, authException) -> {
			LOGGER.info("======URL = " + request.getRequestURI());
			/*
			 * Unauthenticated requests will be routed through this class.
			 * 
			 * If a request is intended to begin the SAML auth workflow, it will be
			 * initiated here {@see IndexController.preAuth()}.
			 * 
			 * Otherwise, the user will be redirected to the pre-auth landing page.
			 */

			if (request.getRequestURI().endsWith("doSaml")) {
				samlEntryPoint.commence(request, response, authException);
			} else {
				response.sendRedirect("/");
			}
		});

		http.addFilterBefore(metadataGeneratorFilter(), ChannelProcessingFilter.class)
				.addFilterAfter(samlFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(samlFilter(), CsrfFilter.class);

		http.authorizeRequests().antMatchers("/**").permitAll();
//		.antMatchers("/pre-auth**").permitAll()
//				.antMatchers("/form-login**").permitAll().antMatchers("/error").permitAll().antMatchers("/saml/**")
//				.permitAll().antMatchers("/css/**").permitAll().antMatchers("/img/**").permitAll().antMatchers("/js/**")
//				.permitAll().antMatchers("/sw.js").permitAll().antMatchers("/saml/addMetaData").permitAll().antMatchers("/addMetaData").permitAll().anyRequest()
//				.authenticated();
	}
	
	/**
     * Define a chain of authentication providers, starting with DB auth.
     *
     * If the user is not supported by DB authentication, it will fall through
     * to the SAML auth provider.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(samlAuthenticationProvider);
    }

	@Override
	public void destroy() throws Exception {
		 this.samlBackgroundTaskTimer.purge();
	        this.samlBackgroundTaskTimer.cancel();
	        this.samlMultiThreadedHttpConnectionManager.shutdown();

	}

}
