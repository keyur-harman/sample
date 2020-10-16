package com.harman.poc.aws.iot;

import java.io.IOException;
import java.util.function.Consumer;

public class TestHandler2 {

	public TestHandler2() {
	}

	public Consumer<String> authorizeUser() {
		return message -> {
			try {
				authorized("123");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Inside excpetion");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

	}

	public void authorized(String s) throws IOException {
		System.out.println("Inside Authorize2");
		System.out.println(Integer.valueOf(s));
	}

}
