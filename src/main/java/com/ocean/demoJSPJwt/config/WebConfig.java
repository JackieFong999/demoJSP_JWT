package com.ocean.demoJSPJwt.config;

import com.ocean.demoJSPJwt.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private SessionInterceptor sessionInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
        System.out.println("=== Registering Interceptor ===");
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", 
                                    "/logout", 
                                    "/login/api",       // JWT login API endpoint
                                    "/webjars/**",
                                    "/css/**",      
                                    "/js/**",       
                                    "/images/**");  
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
        // Serve static resources from /css/, /js/, /images/ directories
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/images/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }
    
}
