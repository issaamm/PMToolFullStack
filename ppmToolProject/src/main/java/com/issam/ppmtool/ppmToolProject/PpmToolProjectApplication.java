package com.issam.ppmtool.ppmToolProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PpmToolProjectApplication {
	
	@Bean
	public BCryptPasswordEncoder passwordEncrypter() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PpmToolProjectApplication.class, args);
	}

}
