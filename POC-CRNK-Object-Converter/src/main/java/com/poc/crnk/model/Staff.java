package com.poc.crnk.model;

import io.crnk.core.resource.annotations.JsonApiId;
import io.crnk.core.resource.annotations.JsonApiResource;

@JsonApiResource(type="staff")
public class Staff {
	
	 @JsonApiId
	  private long id;
	 
	 private String firstName;
	 private String lastName;
	 private String email;
	 
	 
	 public Staff(long id, String firstname, String lastname,String email) {
		 this.id = id;
		 this.firstName = firstname;
		 this.lastName = lastname;
		 this.email = email;
	 }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Staff [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	 
	 

}
