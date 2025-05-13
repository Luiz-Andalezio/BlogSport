package com.spring.blogsport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.spring.blogsport.model")
public class BlogsportApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsportApplication.class, args);
	}

}
