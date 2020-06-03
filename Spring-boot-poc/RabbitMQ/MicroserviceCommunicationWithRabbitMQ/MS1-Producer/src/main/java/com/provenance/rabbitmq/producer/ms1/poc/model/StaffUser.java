package com.provenance.rabbitmq.producer.ms1.poc.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffUser {
	
	private String email;
	private String name;
	private String salary;

}
