package com.releaseday.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:dubbo-provider.xml")
@SpringBootApplication
//@EnableConfigurationProperties(FilterRegistration.class)
public class BootStarter {
	
	public static void main(String[] args) {
		
        ApplicationContext ctx = SpringApplication.run(BootStarter.class, args);
        
    }
	
}