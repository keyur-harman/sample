package com.harman.clinical6.poc.springsecurity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harman.clinical6.poc.springsecurity.model.Student;
 
@RestController
public class WebController {
   
    @RequestMapping(value="/")
    public String home(){
        return "home";
    }
   
    @RequestMapping(value="/user")
    public String user(){
        return "user";
    }
  
    @RequestMapping(value="/admin")
    public String admin(){
        return "admin";
    }
   
    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }
   
    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }
    @RequestMapping(value = "/getSensorDetails/{name}")
	public Student getSensorDetails(@PathVariable(name = "name") String name) {

		return new Student(name, "Pune", " Bla Bla");

	}

	@RequestMapping(value = "/getSensorDetails")
	public Student getSensorDetails1(HttpServletRequest request) {
		return new Student("Raj", "Pune", " Bla Bla");

	}

	@RequestMapping(value = "/postSensorDetails")
	public ResponseEntity<String> returnUserDetailsPost(@RequestBody String user) {

		return new ResponseEntity<>("This is the input: "+user, HttpStatus.OK);

	}


}
