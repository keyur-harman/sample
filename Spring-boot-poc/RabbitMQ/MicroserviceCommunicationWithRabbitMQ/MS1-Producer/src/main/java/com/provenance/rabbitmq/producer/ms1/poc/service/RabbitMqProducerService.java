package com.provenance.rabbitmq.producer.ms1.poc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.provenance.rabbitmq.producer.ms1.poc.model.Car;
import com.provenance.rabbitmq.producer.ms1.poc.model.StaffUser;

@Service
public class RabbitMqProducerService {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMqProducerService.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${ms1.audit.routingkey}")
	private String ROUTING_KEY_AUDIT;

	@Value("${ms1.log.routingkey}")
	private String ROUTING_KEY_LOG;

	@Value("${rmq.exchange}")
	private String EXCHANGE;

	public void sendAuditMessage(StaffUser staffUser) {
		log.info("Sending message "+staffUser);
		amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY_AUDIT, staffUser);
		log.info("Message Sent");
	}

	public void sendLogMessage(Car car) {
		log.info("Sending message "+car);
		amqpTemplate.convertSendAndReceive(EXCHANGE, ROUTING_KEY_LOG, car);
		log.info("Message Sent");
	}

}
