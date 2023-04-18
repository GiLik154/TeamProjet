package com.example.team_project;

import com.example.team_project.controller.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .order(1)
                .excludePathPatterns("/static/css/**")
                .excludePathPatterns("/static/js/**")
                .excludePathPatterns("/*.ico")
                .excludePathPatterns("/main/**")
                .excludePathPatterns("/user/login/**")
                .excludePathPatterns("/user/join/**");
    }
}