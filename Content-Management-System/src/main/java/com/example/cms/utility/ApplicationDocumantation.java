package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class ApplicationDocumantation {

	Contact contact() {
		return new Contact().name("Harsh").url(".com").email("meharshh@gmail.com");
	}
	
	@Bean
	Info info(){
		return new Info().title("Content Management System").description("RESTful API with spring security and auditing on User")
				.version("").contact(contact());
	}
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}
	
}
