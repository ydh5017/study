package com.project.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@MapperScan(value = {"com.project.blog.mapper"})
@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new
				ResourceBundleMessageSource();
		// MesageSource의 인코딩 방식 설정
		messageSource.setDefaultEncoding("utf-8");
		// 메시지를 관리할 파일 이름 지정
		// src/main/resources/**.properties 로 설정(여러개 설정 가능)
		messageSource.setBasenames("messages");
		return messageSource;
	}
}
