package com.provenance.rabbitmq.consumer.topic.exchange.poc.synoma.plugin;

//import com.prahs.esource.exception.EsourceException;
//import com.prahs.esource.jms.models.CustomAmqp;
//import com.prahs.esource.plugin.synoma.exception.local.NotFoundException;
//import com.prahs.esource.plugin.synoma.service.PluginService;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.config.AmqpResponder;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.service.GeneralPluginService;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public interface PluginMessageListener {

	default void handleException(AmqpResponder responder, Message message, Exception e,
			MessageConverter messageConverter) {
		MessageProperties messageProperties = message.getMessageProperties();
		String messageId = messageProperties.getMessageId();
		String replyToDestination = messageProperties.getReplyTo();
		String receivedRoutingKey = messageProperties.getReceivedRoutingKey();

		Integer httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
//        if (e instanceof EsourceException) {
//            httpStatusCode = ((EsourceException) e).getHttpStatus().value();
//        } else if (e instanceof NotFoundException) {
//            httpStatusCode = HttpStatus.NOT_FOUND.value();
//        }
//        CustomAmqp customAmqp = (CustomAmqp) messageConverter.fromMessage(message);
//        responder.sendResponse(replyToDestination, messageId, httpStatusCode, e, customAmqp.getSourceBean().getHttpHeaders());
//        responder.sendResponseToErrorQueue(receivedRoutingKey + ".error", messageId, message, e, customAmqp.getSourceBean().getHttpHeaders());
		HttpHeaders tempHttpHeaders = new HttpHeaders();
		responder.sendResponse(replyToDestination, messageId, httpStatusCode, e, tempHttpHeaders);
		responder.sendResponseToErrorQueue(receivedRoutingKey + ".error", messageId, message, e, tempHttpHeaders);
	}

//
	default void handleMessage(AmqpResponder responder, GeneralPluginService pluginService,
			MessageConverter messageConverter, Message message, String replyToDestination) {
		MessageProperties messageProperties = message.getMessageProperties();
		String messageId = messageProperties.getMessageId();
		if (replyToDestination != null) {
//            CustomAmqp customAmqp = (CustomAmqp) messageConverter.fromMessage(message);
			HttpHeaders tempHttpHeaders = new HttpHeaders();
			responder.sendResponse(replyToDestination, messageId, HttpStatus.OK.value(),
					pluginService.processRequest(message), tempHttpHeaders);
		} else {
			pluginService.processRequest(message);
		}
	}
}
