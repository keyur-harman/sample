package com.provenance.rabbitmq.consumer.topic.exchange.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.provenance.rabbitmq.consumer.topic.exchange.poc.config.RabbitMqConfig;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.model.Config;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.service.RabbitMQService;

@Component
public class QueueServiceImpl {
	
	@Autowired
	RabbitMQService rmqSvc;
	
	public void createInstance(Config instanceDetails) {
		rmqSvc.addQueue(instanceDetails.getConfigId());
	}
	
	public void deleteInstance(Config instanceDetails) {
		rmqSvc.removeQueue(instanceDetails.getConfigId());
	}

}
