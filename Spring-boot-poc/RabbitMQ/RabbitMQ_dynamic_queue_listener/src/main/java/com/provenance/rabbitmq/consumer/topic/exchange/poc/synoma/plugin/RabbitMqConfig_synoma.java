package com.provenance.rabbitmq.consumer.topic.exchange.poc.synoma.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.MessageListener;
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

@Configuration
public class RabbitMqConfig_synoma {
//
//	public static final Set<Binding> staticBindings = new HashSet<>();
//	private static final HashMap<String, MessageListener> handlerMappings = new HashMap<>();
//	@Autowired
//	private GeneralPluginProperties generalPluginProperties;
//	private final PluginProperties pluginProperties;
//	private final DashboardService dashboardService;
//
//	@Autowired
//	public RabbitMqConfig_synoma(PluginProperties pluginProperties,
//			GeneralPluginMessageListener generalPluginMessageListener, DashboardService dashboardService) {
//		this.pluginProperties = pluginProperties;
//		this.dashboardService = dashboardService;
//		handlerMappings.put("pra", generalPluginMessageListener);
//	}
//
//	private static final Logger log = LoggerFactory.getLogger(RabbitMqConfig_synoma.class);
//
//	@Bean
//	public SimpleMessageListenerContainer praContainer(ConnectionFactory connectionFactory) {
//		String module = "pra";
//		return getContainer(connectionFactory, module);
//	}
//
//	private SimpleMessageListenerContainer getContainer(ConnectionFactory connectionFactory, String module) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//		List<String> queues = new ArrayList<>();
//		for (Profile profile : pluginProperties.getProfiles()) {
//			if (profile.getProfileName().equals(module)) {
//				for (String queueName : profile.getQueues()) {
//					queues.add(profile.getProfileName() + "." + queueName);
//				}
//			}
//		}
//		container.setQueueNames(Arrays.copyOf(queues.toArray(), queues.size(), String[].class));
//		container.setMessageListener(handlerMappings.get(module));
//		container.setDefaultRequeueRejected(false);
//		container.setErrorHandler(throwable -> {
//			// Suppress plugin exception
//		});
//		return container;
//	}
//
//    @Bean
//    public Declarables topicBindings() {
//        //Topic Exchange Setup
//        TopicExchange topicExchange = new TopicExchange(pluginProperties.getTopicExchange());
//        List<Queue> queues = new ArrayList<>();
//        List<Binding> bindings = new ArrayList<>();
//        for (Profile profile : pluginProperties.getProfiles()) {
//            for (String queueName : profile.getQueues()) {
//                Queue queue;
//                if (profile.getShouldHandleRetries()) {
//                    queue = QueueBuilder.durable(profile.getProfileName() + "." + queueName)
//                            .withArgument("x-dead-letter-exchange", pluginProperties.getTopicExchange())
//                            .withArgument("x-dead-letter-routing-key", profile.getProfileName() + "." + queueName + ".retry")
//                            .build();
//                } else {
//                    queue = QueueBuilder.durable(profile.getProfileName() + "." + queueName)
//                            .build();
//                }
//                Binding binding = BindingBuilder.bind(queue).to(topicExchange).with(profile.getProfileName() + "." + queueName);
//                queues.add(queue);
//                dashboardService.addQueue(queue.getName());
//                bindings.add(binding);
//                if (profile.getShouldHandleRetries()) {
//                    Queue retryQueue = QueueBuilder.durable(profile.getProfileName() + "." + queueName + ".retry")
//                            .withArgument("x-dead-letter-exchange", pluginProperties.getTopicExchange())
//                            .withArgument("x-dead-letter-routing-key", profile.getProfileName() + "." + queueName)
//                            .withArgument("x-message-ttl", pluginProperties.getRetryDelay()).build();
//                    Binding retryBinding = BindingBuilder.bind(retryQueue).to(topicExchange).with(profile.getProfileName() + "." + queueName + ".retry");
//                    queues.add(retryQueue);
//                    dashboardService.addQueue(retryQueue.getName());
//                    bindings.add(retryBinding);
//                    Queue errorQueue = new Queue(profile.getProfileName() + "." + queueName + ".error", true);
//                    Binding errorBinding = BindingBuilder.bind(errorQueue).to(topicExchange).with(profile.getProfileName() + "." + queueName + ".error");
//                    queues.add(errorQueue);
//                    dashboardService.addQueue(errorQueue.getName());
//                    bindings.add(errorBinding);
//                }
//            }
//        }
//        staticBindings.addAll(bindings);
//        List<Declarable> declarables = new ArrayList<>(queues);
//        declarables.add(topicExchange);
//        declarables.addAll(bindings);
//        return new Declarables(declarables);
//    }
//
}
