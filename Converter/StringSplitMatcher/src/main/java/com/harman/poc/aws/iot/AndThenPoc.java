package com.harman.poc.aws.iot;

import java.util.function.Consumer;

public class AndThenPoc {
	
 public static Consumer<String> messageHandler = (message) -> {
		 
		 System.out.println("Inside the msg 2");
	 };


	public static void main(String[] args) {
		TestHandler testHandler = new TestHandler();
		TestHandler2 ts2 = new TestHandler2();
		Consumer<String> c = (x) -> System.out.println(Integer.valueOf(x));
//		c.andThen(c).accept("Java2s.com");
		
		testHandler.authorizeUser().andThen(messageHandler).accept("KEYUR");
	}

}
