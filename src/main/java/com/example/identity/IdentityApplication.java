package com.example.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class IdentityApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(IdentityApplication.class, args);
	}

	@Bean public RequestContextFilter requestContextListener(){
		return new RequestContextFilter();
	}

//	@Bean
//	public SchemaParserOptions schemaParserOptions(){
//		return SchemaParserOptions.newOptions().objectMapperConfigurer((mapper, context) -> {
//			mapper.registerModule(new JavaTimeModule());
//		}).build();
//	}
//
//	@Bean
//	public ObjectMapperConfigurer objectMapperConfigurer(){
//		return mapper -> mapper.registerModule(new JavaTimeModule())
//			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//	}
}
