package com.thirdleave.readingplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.thirdleave.readingplan.config.PropertiesListener;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableElasticsearchRepositories(basePackages = "com.thridleave.readingplan")
public class ReadingPlan365Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ReadingPlan365Application.class);
		application.addListeners(new PropertiesListener("application.properties"));
		application.run(args);
	}

}
