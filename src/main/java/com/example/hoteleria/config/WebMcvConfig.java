package com.example.hoteleria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMcvConfig implements WebMvcConfigurer {

    public void addCorsMapping(CorsRegistry registry){
        registry.addMapping("/api/v1/clientes")
                .allowedOrigins("http://localhost:5500")
                .allowedMethods("GET","POST");
    }
}
