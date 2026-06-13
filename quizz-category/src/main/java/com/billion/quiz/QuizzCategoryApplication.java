package com.billion.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
public class QuizzCategoryApplication {

	public static void main(String[] args)
	{
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication.run(QuizzCategoryApplication.class, args);
	}

}
