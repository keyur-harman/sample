package com.example.springsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User_Entity {

	@Id
	@GeneratedValue
	@Column
	private long userId;

	private String emailId;

	private String password;

	@Column(name = "name")
	private String name;

	private boolean status; // Active or inActive

	public User_Entity(long userId, String emailId, String password, String name,
			 boolean status) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.password = password;
		this.name = name;
		this.status = status;
	}
	

	@Override
	public String toString() {
		return "User_Entity [userId=" + userId + ", emailId=" + emailId
				+ ", password=" + password + ", name=" + name + ", status="
				+ status + "]";
	}


	public User_Entity() {
		super();
		this.status=false;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}
