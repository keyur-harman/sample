package com.provenance.rabbitmq.consumer.topic.exchange.poc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConsumerConfig {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMqConsumerConfig.class);
	
	@Value("${rmq.all.queue}")
 	private String QUEUE_All;
 	
	@Value("${rmq.exchange}")
    private String EXCHANGE;

	@Value("${ms1.all.routingkey}")
    private String ROUTING_KEY_ALL;
	
    @Bean
    Queue queue_all() {
    	return new Queue(QUEUE_All,true);
    }
    
    @Bean
    TopicExchange exchange() {
    	return new TopicExchange(EXCHANGE);
    }
    
    @Bean
    Binding bindingAllQueue(Queue queue_audit, TopicExchange directExchange) {
    	return BindingBuilder.bind(queue_audit).to(directExchange).with(ROUTING_KEY_ALL);
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
