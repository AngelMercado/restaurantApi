package com.primeted.springRest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter{
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/apiv1/**")
			.allowedOrigins("*")
			.allowedMethods("PUT","DELETE","POST","GET")
			.allowedHeaders("*")
			.allowCredentials(false).maxAge(3600);
	}
}
