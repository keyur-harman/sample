package com.provenance.rabbitmq.consumer.topic.exchange.poc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

import com.provenance.rabbitmq.consumer.topic.exchange.poc.GeneralPluginProperties;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.service.DynamicQueueMessageListener;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.service.GeneralPluginMessageListener;
import com.rabbitmq.client.ShutdownSignalException;

@Configuration
public class RabbitMqConfig {

	@Autowired
	private GeneralPluginProperties generalPluginProperties;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(generalPluginProperties.getRmqUsername());
		connectionFactory.setPassword(generalPluginProperties.getRmqPassword());
		connectionFactory.setHost(generalPluginProperties.getRmqHost());
		connectionFactory.setPort(generalPluginProperties.getRmqPort());
		connectionFactory.setVirtualHost(generalPluginProperties.getRmqVhost());
		return connectionFactory;
	}

	@Bean
	public RabbitAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	/**
	 * This is the default queue generation method. Here it is created the general
	 * plugin queue.
	 * 
	 * @return
	 */
	@Bean
	public Declarables createGeneralPluginQueue() {
		List<Queue> queues = new ArrayList<>();
		List<Binding> bindings = new ArrayList<>();
		TopicExchange topicExchange = new TopicExchange(generalPluginProperties.getTopicExchange());
		String queueName = generalPluginProperties.getProfileName() + "." + generalPluginProperties.getQueue();
		Queue queue;
		if (generalPluginProperties.isShouldHandleRetries()) {
			queue = createQueueWithRetryProerties(queueName);
		} else {
			queue = createQueue(queueName);
		}
		queues.add(queue);
		Binding queueBinding = BindingBuilder.bind(queue).to(topicExchange).with(queueName);
		bindings.add(queueBinding);
		if (generalPluginProperties.isShouldHandleRetries()) {
			String retryQueueName = queueName + ".retry";
			String errorQueueName = queueName + ".error";
			Queue retryQueue = createRetryQueue(retryQueueName);
			Binding retryBinding = BindingBuilder.bind(retryQueue).to(topicExchange).with(retryQueueName);
			queues.add(retryQueue);
			bindings.add(retryBinding);
			Queue errorQueue = createQueue(errorQueueName);
			Binding errorBinding = BindingBuilder.bind(errorQueue).to(topicExchange).with(errorQueueName);
			queues.add(errorQueue);
			bindings.add(errorBinding);
		}
		List<Declarable> declarables = new ArrayList<>(queues);
		declarables.add(topicExchange);
		declarables.addAll(bindings);
		return new Declarables(declarables);
	}

	/**
	 * 
	 * @param connectionFactory
	 * @return SimpleMessageListenerContainer
	 */
	@Bean
	public SimpleMessageListenerContainer generalPluginContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		log.info("Existing queue names in generalPluginContainer = " + Arrays.toString(container.getQueueNames()));
		if (generalPluginProperties.isShouldHandleRetries()) {
			container.addQueueNames(new String[] {
					generalPluginProperties.getProfileName() + "." + generalPluginProperties.getQueue(),
					generalPluginProperties.getProfileName() + "." + generalPluginProperties.getQueue() + ".retry",
					generalPluginProperties.getProfileName() + "." + generalPluginProperties.getQueue() + ".error" });
		} else {
			container
					.addQueueNames(generalPluginProperties.getProfileName() + "." + generalPluginProperties.getQueue());
		}
		container.setMessageListener(new GeneralPluginMessageListener());
		container.setDefaultRequeueRejected(false);
		container.setErrorHandler(throwable -> {
			// Suppress plugin exception
		});
		return container;
	}

	/**
	 * This container is created as a place holder for dynamic queues. Whenever new
	 * queue is created on run time, those queue will be listened on this container.
	 * 
	 * @param connectionFactory
	 * @return SimpleMessageListenerContainer
	 */
	@Bean
	public SimpleMessageListenerContainer dynamicQueueContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setMessageListener(new DynamicQueueMessageListener());
		container.setDefaultRequeueRejected(false);
		container.setErrorHandler(throwable -> {
			// Suppress plugin exception
		});
		return container;
	}

	/**
	 * This defines queue.
	 * 
	 * @param queueName
	 * @return
	 */
	public Queue createQueueWithRetryProerties(String queueName) {
		return QueueBuilder.durable(queueName)
				.withArgument("x-dead-letter-exchange", generalPluginProperties.getTopicExchange())
				.withArgument("x-dead-letter-routing-key", queueName + ".retry").build();
	}

	/**
	 * 
	 * @param queueName
	 * @return
	 */
	public Queue createRetryQueue(String queueName) {
		return QueueBuilder.durable(queueName)
				.withArgument("x-dead-letter-exchange", generalPluginProperties.getTopicExchange())
				.withArgument("x-dead-letter-routing-key", queueName)
				.withArgument("x-message-ttl", generalPluginProperties.getRetryDelay()).build();
	}

	/**
	 * 
	 * @param queueName
	 * @return
	 */
	public Queue createQueue(String queueName) {
		return QueueBuilder.durable(queueName).build();
	}

	/**
	 * This create queue on rabbitmq message broker
	 * 
	 * @param queue
	 * @return
	 */
	public String addQueue(Queue queue) {
		return rabbitAdmin().declareQueue(queue);
	}

	/**
	 * Binds the queue with routing key
	 * 
	 * @param queue
	 * @param routingKey
	 */
	public void addBinding(Queue queue, String routingKey) {
		TopicExchange topicExchange = new TopicExchange(generalPluginProperties.getTopicExchange());
		Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
		rabbitAdmin().declareBinding(binding);
	}

	public void addQueueToMessageListenerer(String queue) {
		SimpleMessageListenerContainer smp = dynamicQueueContainer(connectionFactory());
		smp.addQueueNames(queue);
		log.info("Existing queue names in generalPluginContainer = " + Arrays.toString(smp.getQueueNames()));
	}

	/**
	 * Delete queue, if it is empty and unused.
	 * 
	 * @param queue
	 */
	public void deleteQueue(String queue) {
		rabbitAdmin().deleteQueue(queue, true, true);
	}

	/**
	 * unsubscribe queue from dynamic message listener.
	 * 
	 * @param queue
	 * @return
	 */
	public boolean deleteQueueFromMessageListener(String queue) {
		SimpleMessageListenerContainer smp = dynamicQueueContainer(connectionFactory());
		return smp.removeQueueNames(queue);
	}

}
