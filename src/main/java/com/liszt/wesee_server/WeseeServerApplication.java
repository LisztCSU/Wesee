package com.liszt.wesee_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
@EnableScheduling
@EnableRedisHttpSession
@ComponentScan(basePackages = {"com.liszt.wesee_server.controller","com.liszt.wesee_server.python","com.liszt.wesee_server.push"})
public class WeseeServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeseeServerApplication.class, args);
	}

}
