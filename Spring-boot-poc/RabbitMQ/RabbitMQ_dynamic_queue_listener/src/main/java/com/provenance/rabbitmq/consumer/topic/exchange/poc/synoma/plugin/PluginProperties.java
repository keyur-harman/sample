package com.provenance.rabbitmq.consumer.topic.exchange.poc.synoma.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The file is a java object representation of the plugin.properties file on the classpath.
 * It is recommended that this file not be altered unless you have a clear understanding of the
 * application's inner workings.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@PropertySource("classpath:plugin.properties")
@ConfigurationProperties
public class PluginProperties {
    private String name;
    private String id;
    private String topicExchange;
    private String fanoutExchange;
    private String description;
    private Long retryDelay;
    private Profile[] profiles;
    
}