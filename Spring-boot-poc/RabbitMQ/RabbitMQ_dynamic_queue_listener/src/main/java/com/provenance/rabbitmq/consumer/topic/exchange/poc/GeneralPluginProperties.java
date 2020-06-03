package com.provenance.rabbitmq.consumer.topic.exchange.poc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.provenance.rabbitmq.consumer.topic.exchange.poc.synoma.plugin.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The file is a java object representation of the application.properties file on the classpath.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
//@PropertySource("classpath:gp.application.yml")
@ConfigurationProperties
public class GeneralPluginProperties {
	
	//general plugin properties
	@Value("${profileName}")
	private String profileName;
	@Value("${queue}")
	private String queue;
	@Value("${topicExchange}")
	private String topicExchange;
	@Value("${fanoutExchange}")
	private String fanoutExchange;
	@Value("${shouldHandleRetries}")
	private boolean shouldHandleRetries;
	@Value("${retryDelay}")
	private int retryDelay;
	
	//Rabbitmq properties
	@Value("${spring.rabbitmq.username}")
    private String rmqUsername;
    @Value("${spring.rabbitmq.password}")
    private String rmqPassword;
    @Value("${spring.rabbitmq.host}")
    private String rmqHost;
    @Value("${spring.rabbitmq.port}")
    private int rmqPort;
    @Value("${spring.rabbitmq.vhost}")
    private String rmqVhost;

}
