package com.provenance.rabbitmq.producer.ms1.poc.topic.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provenance.rabbitmq.producer.ms1.poc.model.Car;
import com.provenance.rabbitmq.producer.ms1.poc.model.StaffUser;
import com.provenance.rabbitmq.producer.ms1.poc.service.RabbitMqProducerService;

@RestController
@RequestMapping("/topic-exchange")
public class RmqProducerTopicExchangeController {
	
	private static final Logger log = LoggerFactory.getLogger(RmqProducerTopicExchangeController.class);

	@Autowired
	RabbitMqProducerTopicExchangeService rabbitMqTopicService;

	@PostMapping("/all/{routing_key}")
	public ResponseEntity<String> postMessage(@RequestBody GeneralMessage generalMessage,@PathVariable("routing_key") String routing_key) {
		log.info("on contorller   = " + generalMessage);
		rabbitMqTopicService.sendMessageToAll(generalMessage,routing_key);
		return new ResponseEntity<String>("Message pushed to RabbitMQ", HttpStatus.CREATED);
	}

	
}
