package com.loona.hachathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HachathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(HachathonApplication.class, args);
	}
}
