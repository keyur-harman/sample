package com.provenance.rabbitmq.consumer.topic.exchange.poc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
	
	private String company;
	private String name;
	private String price;

}
