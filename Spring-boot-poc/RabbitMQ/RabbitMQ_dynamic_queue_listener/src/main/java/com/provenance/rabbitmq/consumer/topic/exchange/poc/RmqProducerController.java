package com.provenance.rabbitmq.consumer.topic.exchange.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.provenance.rabbitmq.consumer.topic.exchange.poc.model.Config;


@RestController
@RequestMapping("/configuration")
public class RmqProducerController {
	
	private static final Logger log = LoggerFactory.getLogger(RmqProducerController.class);


	@Autowired
	QueueServiceImpl rabbitMqService;
	
	@PostMapping("/create")
	public ResponseEntity<String> postMessage(@RequestBody Config config) {
		log.info("on contorller /config  = " + config);
		rabbitMqService.createInstance(config);
		return new ResponseEntity<String>("New instance is created", HttpStatus.CREATED);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteConfig(@RequestBody Config config) {
		log.info("on contorller /config  = " + config);
		rabbitMqService.deleteInstance(config);
		return new ResponseEntity<String>("New instance is created", HttpStatus.CREATED);
	}

	
}
