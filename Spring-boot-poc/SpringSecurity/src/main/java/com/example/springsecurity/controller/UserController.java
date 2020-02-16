package com.example.springsecurity.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.entity.User_Entity;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserService;

@RestController
public class UserController {

	public static final long JWT_TOKEN_VALIDITY = 24*60*60;
	String token;
	private final String TOKEN_SECRET = "h4of9eh48vmg02nfu30v27yen295hfj65";

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtillController jwtUtilController;

	@PostMapping(value = "/createuser")
	public ResponseEntity<User_Entity> addUser(@RequestBody User_Entity user) {

		System.out.println("user is " + user);
		userRepository.save(user);
		System.out.println("user is inserted sucessfully inside the DB");
		Date expiryDate = new Date(System.currentTimeMillis()
				+ JWT_TOKEN_VALIDITY * 1000);
		System.out.println("expiryDate::::::::" + expiryDate);

		token = Jwts.builder().setSubject(Long.toString(user.getUserId()))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
				.compact();

		System.out.println("token::::::::" + token);
		// jwtUtilController.validateToken(token, user);

		return new ResponseEntity<User_Entity>(user, HttpStatus.CREATED);
	}

	@GetMapping(value = "/validateToken/{token}")
	public String validateToken(@PathVariable("token") String token) {
		jwtUtilController.validateToken(token);
		return "Token validation is done";

	}

}