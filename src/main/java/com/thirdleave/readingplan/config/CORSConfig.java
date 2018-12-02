package com.thirdleave.readingplan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class CORSConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
		// CorsRegistration addMapping = registry.addMapping("/api/**");
		// CorsRegistration allowedOrigins =
		// addMapping.allowedOrigins("http://localhost");
		// CorsRegistration allowedMethods = allowedOrigins.allowedMethods("GET",
		// "POST");
		// allowedMethods.allowCredentials(false).maxAge(3600);
	}
}
