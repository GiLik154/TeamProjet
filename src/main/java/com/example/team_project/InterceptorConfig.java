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
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/seller/**")
                .excludePathPatterns("/product/seller/**")
                .excludePathPatterns("/main/**")
                .excludePathPatterns("/seller/sellerEmailInputForm/**")
                .excludePathPatterns("/seller/sellerPasswordUpdateForm/**")
                .excludePathPatterns("/seller/join-form/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/post/list/**")
                .excludePathPatterns("/post/read/**")
                .excludePathPatterns("/order_list/**");

        registry.addInterceptor(new SellerLoginInterceptor())
                .order(1)
                .addPathPatterns("/seller/**");

    }
}