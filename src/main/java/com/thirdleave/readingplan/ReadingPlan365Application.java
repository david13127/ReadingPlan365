package com.thirdleave.readingplan;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ReadingPlan365Application {

	public static void main(String[] args) {
		SpringApplication.run(ReadingPlan365Application.class, args);
	}
}
