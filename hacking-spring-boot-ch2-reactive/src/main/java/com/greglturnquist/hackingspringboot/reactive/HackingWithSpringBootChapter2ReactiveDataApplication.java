package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;

import reactor.blockhound.BlockHound;

@SpringBootApplication
public class HackingWithSpringBootChapter2ReactiveDataApplication {

	public static void main(String[] args) {
//		하운드 사용 코드 생략
//		BlockHound.builder()
//		.allowBlockingCallsInside(
//				TemplateEngine.class, getCanonicalName(), "process")
//		.install();
		
		SpringApplication.run(HackingWithSpringBootChapter2ReactiveDataApplication.class, args);
	}

}
