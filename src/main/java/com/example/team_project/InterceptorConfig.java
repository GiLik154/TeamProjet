package com.example.team_project;

import com.example.team_project.controller.interceptor.SellerLoginInterceptor;
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
                .excludePathPatterns("/product/detail/**") // 제외 대신 포함시킴
                .excludePathPatterns("/shop/**")
                .excludePathPatterns("/seller/**")
                .excludePathPatterns("/product/seller/**")
                .excludePathPatterns("/main/**")
                .excludePathPatterns("/seller/sellerEmailInputForm/**")
                .excludePathPatterns("/seller/sellerPasswordUpdateForm/**")
                .excludePathPatterns("/user/login/**")
                .excludePathPatterns("/seller/join-form/**")
                .excludePathPatterns("/user/join/**");

        registry.addInterceptor(new SellerLoginInterceptor())
                .order(1)
                .excludePathPatterns("/static/css/**")
                .excludePathPatterns("/static/js/**")
                .excludePathPatterns("/*.ico")
                .excludePathPatterns("/seller/login-form/**")
                .excludePathPatterns("/seller/searchId/**")
                .excludePathPatterns("/seller/searchPassword/**")
                .excludePathPatterns("/seller/join-form/**")
                .excludePathPatterns("/seller/sellerEmailInputForm/**")
                .excludePathPatterns("/product/detail/**")
                .excludePathPatterns("/seller/sellerPasswordUpdateForm/**")
                .excludePathPatterns("/seller/update/**")
                .excludePathPatterns("/seller/join-form/**")
                .excludePathPatterns("/update/like/**")
                .excludePathPatterns("/shop/join/**")
                .excludePathPatterns("/main/**")
                .excludePathPatterns("/user/login/**")
                .excludePathPatterns("/user/signup/**")
                .excludePathPatterns("/post/list/**")
                .excludePathPatterns("/post/read/**");
    }
}