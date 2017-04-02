package com.releaseday.consumer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.releaseday.consumer.jwt.filter.HTTPBasicAuthorizeAttribute;
import com.releaseday.consumer.jwt.filter.HTTPBearerAuthorizeAttribute;

@Configuration
@ConfigurationProperties
public class FilterRegistration {
	
	@Bean
	public FilterRegistrationBean basicFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();
		registrationBean.setFilter(httpBasicFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/user/getuser");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean jwtFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();
		registrationBean.setFilter(httpBearerFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/sbprdc/manage/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
}
