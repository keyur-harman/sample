package com.provenance.rabbitmq.consumer.topic.exchange.poc.service;

import org.springframework.amqp.core.Message;

public interface GeneralPluginService {
    String processRequest(Message message);
}
