package com.provenance.rabbitmq.producer.ms1.poc.topic.exchange;

import java.util.HashMap;

import com.provenance.rabbitmq.producer.ms1.poc.model.Car;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralMessage {
	
	private HashMap<String, String> header;
	private double corelationId;
	private String payload;

}
