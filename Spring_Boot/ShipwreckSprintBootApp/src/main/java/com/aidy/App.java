package com.aidy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** First Spring Boot Application
 * @author aidys
 *
 */

/*
 */

import org.springframework.data.jpa.repository.JpaRepository;
@SpringBootApplication

public class App {
	JpaRepository<String, Long> a;
	public static void main(String args[]) {
		SpringApplication.run(App.class, args);
	}
}
