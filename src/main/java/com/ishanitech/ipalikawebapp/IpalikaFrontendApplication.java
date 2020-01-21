package com.ishanitech.ipalikawebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IpalikaFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpalikaFrontendApplication.class, args);
	}
	
	 //Create bean for rest template. Using rest template we can call remote rest api.
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
