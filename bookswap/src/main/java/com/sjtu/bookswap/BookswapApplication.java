package com.sjtu.bookswap;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class BookswapApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookswapApplication.class, args);
	}
	
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        factory.setMaxFileSize("102400KB"); 
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  
}
