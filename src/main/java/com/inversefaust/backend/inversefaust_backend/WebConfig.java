package com.inversefaust.backend.inversefaust_backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/") // "/api/" 경로에 대한 CORS 설정
                .allowedOrigins("http://localhost:5173") // 허용할 오리진 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 설정
                .allowCredentials(true); // 인증 정보(쿠키 등) 허용
    }
}