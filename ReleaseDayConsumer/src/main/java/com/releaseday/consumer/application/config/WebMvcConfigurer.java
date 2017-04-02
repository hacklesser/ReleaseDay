package com.releaseday.consumer.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.releaseday.consumer.application.interceptor.UserOptLogInterceptor;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter{
	public void addInterceptors(InterceptorRegistry registry) {
		UserOptLogInterceptor userOptLogInterceptor = new UserOptLogInterceptor();
        registry.addInterceptor(userOptLogInterceptor);
    }
}
