package com.kg.poc.crnk.model;

import io.crnk.core.resource.annotations.JsonApiId;

public class Organization {

	@JsonApiId
	private long id;
	
	private String name;
	
	public Organization() {
		
	}
	
	public Organization(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + "]";
	}
	
	
}
