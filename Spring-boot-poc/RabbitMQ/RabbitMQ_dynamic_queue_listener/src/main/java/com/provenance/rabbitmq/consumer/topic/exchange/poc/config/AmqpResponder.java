package com.provenance.rabbitmq.consumer.topic.exchange.poc.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.prahs.esource.exception.PluginException;
//import com.prahs.esource.jms.models.EmptyMessage;
//import com.prahs.esource.plugin.synoma.config.properties.PluginProperties;
import com.provenance.rabbitmq.consumer.topic.exchange.poc.GeneralPluginProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AmqpResponder {

    private final RabbitTemplate sender;
    private final GeneralPluginProperties pluginProperties;
    private final ObjectMapper objectMapper;

    public AmqpResponder(RabbitTemplate sender, GeneralPluginProperties pluginProperties, ObjectMapper objectMapper) {
        this.sender = sender;
        this.pluginProperties = pluginProperties;
        this.objectMapper = objectMapper;
    }

    // DEAD LETTER
    public void sendResponseToErrorQueue(String routingKey, String correlationId, Message message, Exception thrownException, HttpHeaders httpHeaders) {
        List<Map<String, ?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
        Long retryCount = 0L;
        if (xDeathHeader != null) {
            retryCount = (Long) xDeathHeader.get(0).get("count");
        }
        RetryInfo retryInfo = new RetryInfo(
                "Plugin Exception",
                Integer.valueOf(retryCount.toString()),
                ZonedDateTime.now(),
                new AmqpResponder.MessageProperties(
                        message.getMessageProperties().getContentType(),
                        message.getMessageProperties().getContentEncoding(),
                        message.getMessageProperties().getHeaders(),
                        message.getMessageProperties().getDeliveryMode() != null ? message.getMessageProperties().getDeliveryMode().ordinal() : null,
                        message.getMessageProperties().getPriority(),
                        message.getMessageProperties().getMessageId()
                ),
                thrownException.getClass().getCanonicalName(),
                thrownException.getMessage(),
                Arrays.stream(thrownException.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining())
        );
        routingKey = routingKey.replaceAll("queue://", "");
        routingKey = routingKey.replaceAll("toolkit://", "");
        // end of the part I'm not in love with
        log.trace(routingKey, correlationId, "error response object");
        sender.convertAndSend(pluginProperties.getTopicExchange(), routingKey, message, m -> {
            String retryInfoAsJson;
            try {
                retryInfoAsJson = this.objectMapper.writeValueAsString(retryInfo);
            } catch (JsonProcessingException e) {
                retryInfoAsJson = "Could not parse retryInfo";
            }
            httpHeaders.forEach((key, value) -> m.getMessageProperties().setHeader(key, value.stream().reduce((word1, word2) -> word1 + word2).orElse("")));
            m.getMessageProperties().setHeader("retry-info", retryInfoAsJson);
            m.getMessageProperties().setHeader(AmqpHeaders.TYPE, message.getClass().getCanonicalName());
            m.getMessageProperties().setHeader(AmqpHeaders.CORRELATION_ID, correlationId);
            return m;
        });
    }

    // OBJECT RESPONSE
    public <T> void sendObjectResponse(String routingKey, String correlationId, Integer responseStatus, T responseObject, HttpHeaders httpHeaders) {
        log.trace(routingKey, correlationId, "error response object");
        Object repsonse = null;
        if (responseObject != null) {
            repsonse = responseObject;
        }
        sender.convertAndSend(pluginProperties.getTopicExchange(), routingKey, repsonse, m -> {
            httpHeaders.forEach((key, value) -> m.getMessageProperties().setHeader(key, value.stream().reduce((word1, word2) -> word1 + word2).orElse("")));
            m.getMessageProperties().setHeader(AmqpHeaders.PREFIX + "responseCode", responseStatus);
            m.getMessageProperties().setHeader(AmqpHeaders.CORRELATION_ID, correlationId);
            return m;
        });
    }

    // MAIN RESPONSE
    public <T> void sendResponse(String routingKey, String correlationId, Integer responseStatus, T responseObject, HttpHeaders httpHeaders) {
        log.trace(routingKey, correlationId, "error response object");
        String responseAsString = "{}";
        if (responseObject != null) {
            try {
                responseAsString = objectMapper.writeValueAsString(responseObject);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        sender.convertAndSend(pluginProperties.getTopicExchange(), routingKey, responseAsString, m -> {
            httpHeaders.forEach((key, value) -> m.getMessageProperties().setHeader(key, value.stream().reduce((word1, word2) -> word1 + word2).orElse("")));
            m.getMessageProperties().setHeader(AmqpHeaders.PREFIX + "responseCode", responseStatus);
            m.getMessageProperties().setHeader(AmqpHeaders.CORRELATION_ID, correlationId);
            return m;
        });
    }

    public <T> void sendBroadcast(String routingKey, T message) {
        sender.convertAndSend(pluginProperties.getFanoutExchange(), routingKey, message);
    }

    @Data
    @AllArgsConstructor
    private static class RetryInfo {
        private String error;
        private Integer numAttempts;
        private ZonedDateTime failedAt;
        private AmqpResponder.MessageProperties properties;
        private String errorClass;
        private String errorMessage;
        private String backtrace;
    }

    @Data
    @AllArgsConstructor
    private static class MessageProperties {
        private String contentType;
        private String contentEncoding;
        private Map<String, Object> headers;
        private Integer deliveryMode;
        private Integer priority;
        private String messageId;

    }


}
