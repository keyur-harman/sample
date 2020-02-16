package com.harman.clinical6.poc.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Class which checks for jdbc authentication and configurations for database spring security
 * @author RChoudhury2
 *
 */
@Configuration
@EnableAutoConfiguration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	/**
	 * this method checks for jdbc authentication
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
						"select username,password, enabled from users where username=?")
				.authoritiesByUsernameQuery(
						"select username, role from user_roles where username=?");
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf()
				.disable()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.withObjectPostProcessor(
						new ObjectPostProcessor<FilterSecurityInterceptor>() {
							public <O extends FilterSecurityInterceptor> O postProcess(
									O fsi) {
								FilterInvocationSecurityMetadataSource newSource = new MyFilterSecurityMetadataSource();
								fsi.setSecurityMetadataSource(newSource);

								fsi.setAccessDecisionManager(new MyAccessDecisionManager());
								return fsi;
							}
						}).and().httpBasic();
	}
}