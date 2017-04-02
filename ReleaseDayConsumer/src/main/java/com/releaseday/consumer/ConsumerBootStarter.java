package com.releaseday.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:dubbo-consumer.xml")
@SpringBootApplication
//@EnableConfigurationProperties(FilterRegistration.class)
public class ConsumerBootStarter {
	
	public static void main(String[] args) {
		
        ApplicationContext ctx = SpringApplication.run(ConsumerBootStarter.class, args);
        
    }
	
}