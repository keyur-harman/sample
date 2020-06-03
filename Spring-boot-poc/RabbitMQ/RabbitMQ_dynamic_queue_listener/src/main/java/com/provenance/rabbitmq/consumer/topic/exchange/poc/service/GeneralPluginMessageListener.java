package com.provenance.rabbitmq.consumer.topic.exchange.poc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Component
public class GeneralPluginMessageListener implements MessageListener {

	private static final Logger log = LoggerFactory.getLogger(GeneralPluginMessageListener.class);

	@Autowired
	public GeneralPluginMessageListener() {
	}

	@Override
	public void onMessage(Message message) {
		try {
			messageHandler.accept(message);
		} catch (Exception e) {
		}
	}

	public Consumer<Message> messageHandler = (message) -> {
//      handleMessage(responder, praMapperService, messageConverter, message, message.getMessageProperties().getReplyTo());
		log.info("message id : " + message.getMessageProperties().getMessageId() + ", replyto : "
				+ message.getMessageProperties().getReplyTo() + ", correlationId : "
				+ message.getMessageProperties().getCorrelationId() + ", routing key : "
				+ message.getMessageProperties().getReceivedRoutingKey() + ", exchnage : "
				+ message.getMessageProperties().getReceivedExchange() + ", userid : "
				+ message.getMessageProperties().getReceivedUserId() + ", body :  " + message.getBody());
	};

}
