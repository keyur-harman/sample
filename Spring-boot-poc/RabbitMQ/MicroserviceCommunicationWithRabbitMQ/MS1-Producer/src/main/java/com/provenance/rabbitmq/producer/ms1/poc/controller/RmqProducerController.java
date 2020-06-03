package com.provenance.rabbitmq.producer.ms1.poc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provenance.rabbitmq.producer.ms1.poc.model.Car;
import com.provenance.rabbitmq.producer.ms1.poc.model.StaffUser;
import com.provenance.rabbitmq.producer.ms1.poc.service.RabbitMqProducerService;

@RestController
@RequestMapping("/direct-exchange")
public class RmqProducerController {
	
	private static final Logger log = LoggerFactory.getLogger(RmqProducerController.class);

	@Autowired
	RabbitMqProducerService rabbitMqService;

	@PostMapping("/audit")
	public ResponseEntity<String> postMessage(@RequestBody StaffUser staffUser) {
		log.info("on contorller /audit  = " + staffUser);
		rabbitMqService.sendAuditMessage(staffUser);
		return new ResponseEntity<String>("Message pushed to RabbitMQ", HttpStatus.CREATED);
	}

	@PostMapping("/log")
	public ResponseEntity<String> postMessage2(@RequestBody Car car) {
		log.info("on contorller /log  = " + car);
		rabbitMqService.sendLogMessage(car);
		return new ResponseEntity<String>("Message pushed to RabbitMQ", HttpStatus.CREATED);
	}
	
}
