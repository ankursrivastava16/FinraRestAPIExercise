package com.finra.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinraRestApiApplication {
	
	/**
	 * This application has been tested using Postman requests to upload a file and its meta deta on local file system.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(FinraRestApiApplication.class, args);
	}
}
