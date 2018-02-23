package com.learncamel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ActiveMQCamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveMQCamelApplication.class, args);
	}
}
