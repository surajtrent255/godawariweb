package com.ishanitech.ipalikawebapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@code WebViewConfig} is a configuration class that implements {@code WebMvcConfigurer} interface. 
 * This configuration class is useful in those cases where controller class returns only view.
 * For example: About controller returning only about us view to the user.
 * @author <b> Umesh Bhujel
 * @since 1.0
 */
@Configuration
public class WebViewConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/home").setViewName("index");
		registry.addViewController("/index").setViewName("index");
	}
	
	
}
