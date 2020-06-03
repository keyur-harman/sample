package com.provenance.rabbitmq.consumer.poc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
	
	private String company;
	private String name;
	private String price;

}
