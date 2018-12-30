package com.thirdleave.readingplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import com.thirdleave.readingplan.config.PropertiesListener;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ReadingPlan365Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ReadingPlan365Application.class);
		application.addListeners(new PropertiesListener("application.properties"));
		application.run(args);
	}

}
