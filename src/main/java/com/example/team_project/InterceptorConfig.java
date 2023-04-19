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
                //경로추가 seller
                .excludePathPatterns("/seller/login-form/**")
                .excludePathPatterns("/seller/searchId/**")
                .excludePathPatterns("/seller/searchPassword/**")
                .excludePathPatterns("/seller/update/**")
                .excludePathPatterns("/seller/join-form/**")
                .excludePathPatterns("/seller/sellerEmailInputForm/**")
                .excludePathPatterns("/seller/sellerPasswordUpdateForm/**")
                .excludePathPatterns("/seller/sellerIndex/**")
                .excludePathPatterns("/seller/delete/**")
                //shop
                .excludePathPatterns("/shop/list/**")
                .excludePathPatterns("/shop/update/**")
                .excludePathPatterns("/shop/delete/**")
                .excludePathPatterns("/shop/join/**")
                //product
                .excludePathPatterns("/product/detail/**")
                .excludePathPatterns("/product/delete/**")
                .excludePathPatterns("/product/seller/list/**")
                .excludePathPatterns("/product/update/**")







                //
                .excludePathPatterns("/main/**")
                .excludePathPatterns("/user/login/**")
                .excludePathPatterns("/user/join/**");

    }
}


// java 서블릿 mvc
//Html -> 언어 x ->>
