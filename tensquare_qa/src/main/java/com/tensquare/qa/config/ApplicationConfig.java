package com.tensquare.qa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.tensquare.qa.filter.JwtFilter;

public class ApplicationConfig  extends WebMvcConfigurationSupport{
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtFilter).addPathPatterns("/**").excludePathPatterns("/**/login");
	}
}