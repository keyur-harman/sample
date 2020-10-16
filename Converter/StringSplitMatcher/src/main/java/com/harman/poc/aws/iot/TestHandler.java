package com.harman.poc.aws.iot;

import java.io.IOException;
import java.util.function.Consumer;


public class TestHandler {

	public TestHandler() {
	}
	
	
	public Consumer<String> authorizeUser() {
		return message -> {
				authorized("ddd");
			
		};

	}

	public void authorized(String s)  {
		System.out.println("Inside Authorize");
			System.out.println(Integer.valueOf(s));
			throw new NumberFormatException();
		
	}

}
