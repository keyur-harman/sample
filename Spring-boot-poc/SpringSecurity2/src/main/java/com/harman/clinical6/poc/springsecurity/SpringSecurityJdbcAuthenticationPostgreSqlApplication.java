package com.harman.clinical6.poc.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application implementing Spring Security
 *
 */
@SpringBootApplication
public class SpringSecurityJdbcAuthenticationPostgreSqlApplication {

	/**
	 * main method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJdbcAuthenticationPostgreSqlApplication.class, args);
	}
}
