package com.provenance.rabbitmq.consumer.topic.exchange.poc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.provenance.rabbitmq.consumer.topic.exchange.poc.GeneralPluginProperties;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.config.RabbitMqConfig;

@Service
public class RabbitMQService {

	@Autowired
	RabbitMqConfig rmqConfig;

	@Autowired
	GeneralPluginProperties generalPluginProperties;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * This method adds new queue on run time. Here it is create the queue based on
	 * provided name as parameter. Also it binds the queue with routing key, here
	 * routing key would be same as queue name. Once queue is created, then adds the
	 * queue name to Dynamic message listener, to receive the messages.
	 * 
	 * @param queueName
	 */
	public void addQueue(String queueName) {
		Queue createdQueue = null;
		Queue retryQueue = null;
		Queue errorQueue = null;
		String retryQueueName = queueName + ".retry";
		String errorQueueName = queueName + ".error";
		if (generalPluginProperties.isShouldHandleRetries()) {
			createdQueue = rmqConfig.createQueueWithRetryProerties(queueName);
			retryQueue = rmqConfig.createRetryQueue(retryQueueName);
			errorQueue = rmqConfig.createQueue(errorQueueName);
		} else {
			createdQueue = rmqConfig.createQueue(queueName);
		}
		rmqConfig.addQueue(createdQueue);
		rmqConfig.addBinding(createdQueue, queueName);
		rmqConfig.addQueueToMessageListenerer(createdQueue.getActualName());
		log.debug("Queue is created with name " + queueName);

		if (retryQueue != null) {
			rmqConfig.addQueue(retryQueue);
			rmqConfig.addBinding(retryQueue, retryQueueName);
			rmqConfig.addQueueToMessageListenerer(retryQueueName);
		}

		if (errorQueue != null) {
			rmqConfig.addQueue(errorQueue);
			rmqConfig.addBinding(errorQueue, errorQueueName);
			rmqConfig.addQueueToMessageListenerer(errorQueueName);
		}
	}

	public void removeQueue(String queueName) {
		rmqConfig.deleteQueueFromMessageListener(queueName);
//		if (deleteQueueFromMessageListener(queueName)) {
		rmqConfig.deleteQueue(queueName);
		if (generalPluginProperties.isShouldHandleRetries()) {
			rmqConfig.deleteQueue(queueName + ".retry");
			rmqConfig.deleteQueue(queueName + ".error");
		}
//		}
	}

}
