package com.thirdleave.readingplan;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.thirdleave.readingplan.config.PropertiesListener;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class ReadingPlan365Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReadingPlan365Application.class);
        application.addListeners(new PropertiesListener("application.properties"));
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}
