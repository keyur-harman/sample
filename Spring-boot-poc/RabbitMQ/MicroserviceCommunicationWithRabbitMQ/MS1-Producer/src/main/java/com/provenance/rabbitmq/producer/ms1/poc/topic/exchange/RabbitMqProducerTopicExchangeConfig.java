package com.provenance.rabbitmq.producer.ms1.poc.topic.exchange;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
public class RabbitMqProducerTopicExchangeConfig {
	
//		@Value("${rmq.all.queue}")
//	 	private String QUEUE_ALL;
	 	
	

		@Value("${rmq.topic.exchange}")
	    private String EXCHANGE;

//		@Value("${rmq.all.routingkey}")
//	    private String ROUTING_KEY_ALL;
	    
		// Following code generate queue and do binding with exchange on run time.
//	    @Bean
//	    Queue queue_all() {
//	    	return new Queue(QUEUE_ALL,true);
//	    }
		
//	    @Bean
//	    Binding bindingAllQueue(Queue queue_all, TopicExchange topicExchange) {
//	    	return BindingBuilder.bind(queue_all).to(topicExchange).with(ROUTING_KEY_ALL);
//	    }
	    	    
	    @Bean
	    TopicExchange topicExchange() {
	    	return new TopicExchange(EXCHANGE);
	    }
	    
	    @Bean
	    public MessageConverter topicExchangeJsonMessageConverter() {
	    	return new Jackson2JsonMessageConverter();
	    }
	    
	    

}
