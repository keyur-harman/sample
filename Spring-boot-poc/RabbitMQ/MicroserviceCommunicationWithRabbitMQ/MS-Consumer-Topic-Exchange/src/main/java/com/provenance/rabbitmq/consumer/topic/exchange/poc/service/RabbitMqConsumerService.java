package com.provenance.rabbitmq.consumer.topic.exchange.poc.service;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.stereotype.Service;

import com.provenance.rabbitmq.consumer.topic.exchange.poc.model.Car;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.model.StaffUser;

@Service
public class RabbitMqConsumerService {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMqConsumerService.class);


	@RabbitListener(queues = "${rmq.all.queue}")
	public void receiveAllMessage(final Message message) {
		log.info("Received generic message on All queue >>" + message);
		log.info("Headers = "+message.getMessageProperties().getHeaders());
		log.info("Routing key = "+message.getMessageProperties().getReceivedRoutingKey());
//		log.info("Correlation Id = "+message.getMessageProperties().getCorrelationId());
		log.info("Correlation Id = "+message.getMessageProperties().getHeader(AmqpHeaders.CORRELATION_ID));
		log.info("Message body = "+message.getBody());
		try {
			log.info("Message body = "+new String(message.getBody(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String routingKey = message.getMessageProperties().getReceivedRoutingKey();
		
		if(routingKey.equals("pra.service.config.create")) {
			log.info("-------- Inside configuration : Create operation -------------");
			
		}else if(routingKey.equals("pra.service.config.update")) {
			log.info("-------- Inside configuration : Update operation -------------");
			
		}else if(routingKey.equals("pra.service.config.view")) {
			log.info("-------- Inside configuration : view operation -------------");
			
		}else if(routingKey.equals("pra.service.config.delete")) {
			log.info("-------- Inside configuration : delete operation -------------");
			
		}
		else if(routingKey.equals("pra.service.stats")) {
			log.info("-------- Inside stats -------------");
		}else {
			
			log.info("-------- Routing key not mapped with any config and stats routing key -------------");
		}
	}


}
