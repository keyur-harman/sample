package com.example.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.springsecurity.entity.User_Entity;
import com.example.springsecurity.repository.UserRepository;


@Service
@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public void createUser(User_Entity user) {
		System.out.println("user"+user);
		userRepository.save(user);
		
	}
	
	
	
	

}
