package com.liszt.wesee_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.liszt.wesee_server.controller"})
public class WeseeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeseeServerApplication.class, args);
	}

}
