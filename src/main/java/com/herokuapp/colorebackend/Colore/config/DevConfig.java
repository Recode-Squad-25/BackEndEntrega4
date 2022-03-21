package com.herokuapp.colorebackend.Colore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.herokuapp.colorebackend.Colore.service.DBService;
import com.herokuapp.colorebackend.Colore.service.EmailService;
import com.herokuapp.colorebackend.Colore.service.SmtpEmailService;


@Configuration
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() {

		if (!"create".equals(strategy)) {
			return false;
		}

		dbService.instantiateDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
