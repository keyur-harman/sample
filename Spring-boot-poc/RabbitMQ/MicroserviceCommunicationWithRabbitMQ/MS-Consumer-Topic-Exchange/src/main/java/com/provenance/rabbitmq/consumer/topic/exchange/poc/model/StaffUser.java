package com.provenance.rabbitmq.consumer.topic.exchange.poc.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffUser {
	
	private String email;
	private String name;
	private String salary;

}
