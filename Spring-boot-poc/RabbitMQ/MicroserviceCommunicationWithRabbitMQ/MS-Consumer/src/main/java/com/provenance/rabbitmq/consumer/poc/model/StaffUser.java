package com.provenance.rabbitmq.consumer.poc.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffUser {
	
	private String email;
	private String name;
	private String salary;

}
