package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurity.entity.User_Entity;

public interface UserRepository  extends JpaRepository<User_Entity, String>{
	
	User_Entity findByEmailIdIgnoreCase(String emailId);
	
	User_Entity findByUserId(String userId);
	}


