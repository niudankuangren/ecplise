package com.tensquare.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import util.IdWorker;
import util.SmsUtil;



@SpringBootApplication
public class RabbitmqApplication {
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
		
	}

	@Autowired
	private SmsUtil smsUtil;
	
	@Bean
	public SmsUtil smsUtil(){
		return new SmsUtil();
	}
}
