package com.provenance.rabbitmq.consumer.poc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.provenance.rabbitmq.consumer.poc.model.Car;
import com.provenance.rabbitmq.consumer.poc.model.StaffUser;

@Service
public class RabbitMqConsumerService {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMqConsumerService.class);


	@RabbitListener(queues = "${rmq.audit.queue}")
	public void receiveAuditMessage(StaffUser staffUser) {
		log.info("Received Message from Audit Queue >>" + staffUser);
	}

	@RabbitListener(queues = "${rmq.log.queue}")
	public void receiveLogMessage(Car car) {
		log.info("Received Message from Log Queue >>" + car);
	}

}
