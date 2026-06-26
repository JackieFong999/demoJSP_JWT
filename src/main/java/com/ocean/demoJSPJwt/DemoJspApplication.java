package com.ocean.demoJSPJwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

// @SpringBootApplication
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@ComponentScan(basePackages = {"com.ocean.demoJSPJwt"})  // 21/05/2026(Add Interceptor) Make sure to scan all packages
public class DemoJspApplication {

	
    // @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoJspApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(DemoJspApplication.class, args);
	}
}
