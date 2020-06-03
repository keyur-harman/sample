package com.provenance.rabbitmq.consumer.poc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.AmqpContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConsumerConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMqConsumerConfig.class);
	
	@Value("${rmq.audit.queue}")
 	private String QUEUE_AUDIT;
 	
	@Value("${rmq.log.queue}")
 	private String QUEUE_LOG;

	@Value("${rmq.exchange}")
    private String EXCHANGE;

	@Value("${ms1.audit.routingkey}")
    private String ROUTING_KEY_AUDIT;
    
	@Value("${ms1.log.routingkey}")
    private String ROUTING_KEY_LOG;
	
    @Bean
    Queue queue_audit() {
    	return new Queue(QUEUE_AUDIT,true);
    }
    
    @Bean
    Queue queue_log() {
    	return new Queue(QUEUE_LOG,true);
    }
    	    
    @Bean
    DirectExchange exchange() {
    	return new DirectExchange(EXCHANGE);
    }
    
    @Bean
    Binding bindingAuditQueue(Queue queue_audit, DirectExchange directExchange) {
    	return BindingBuilder.bind(queue_audit).to(directExchange).with(ROUTING_KEY_AUDIT);
    }
    
    @Bean
    Binding bindingLogQueue(Queue queue_log, DirectExchange directExchange) {
    	return BindingBuilder.bind(queue_log).to(directExchange).with(ROUTING_KEY_LOG);
    }
	
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    	log.info("ConnectionFactory = "+connectionFactory.getHost()+connectionFactory.getPort()+connectionFactory.getUsername());
    	final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    	rabbitTemplate.setMessageConverter(jsonMessageConverter());
    	return rabbitTemplate;
    }
    

}
