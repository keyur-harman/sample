package com.provenance.rabbitmq.producer.ms1.poc.topic.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqProducerTopicExchangeService {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitMqProducerTopicExchangeService.class);

	@Autowired
	private AmqpTemplate amqpTemplate;
	


	@Value("${rmq.topic.exchange}")
	private String EXCHANGE;

	public void sendMessageToAll(GeneralMessage generalMessage,String routingkey) {
		log.info("Sending message "+generalMessage);
		MessageProperties messagProperties = new MessageProperties();
		String responseAsString = "{}";
		if(generalMessage.getPayload() != null) {
			responseAsString = generalMessage.getPayload();
		}
		
		Message msg = new Message(responseAsString.getBytes(), messagProperties);
		
		for (String key : generalMessage.getHeader().keySet()) {
			msg.getMessageProperties().setHeader(key, generalMessage.getHeader().get(key));
		}	
		
		msg.getMessageProperties().setHeader(AmqpHeaders.CORRELATION_ID, String.valueOf(generalMessage.getCorelationId()));
//		amqpTemplate.convertAndSend(EXCHANGE, routingkey, m -> {
//			generalMessage.getHeader().forEach((key, value) -> m.getMessageProperties().setHeader(key, generalMessage.getHeader().get(key)));
//            m.getMessageProperties().setHeader(AmqpHeaders.PREFIX + "responseCode", 200);
//            m.getMessageProperties().setHeader(AmqpHeaders.CORRELATION_ID, String.valueOf(generalMessage.getCorelationId()));
//            return m;
//        });
		amqpTemplate.convertAndSend("",routingkey,msg);
		
		
		log.info("Message Sent");
	}


}
