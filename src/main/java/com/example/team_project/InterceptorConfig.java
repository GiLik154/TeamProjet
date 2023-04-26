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
                .excludePathPatterns("/shop/**")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/seller/**")
                .excludePathPatterns("/main/**")
                .excludePathPatterns("/seller/sellerEmailInputForm/**")
                .excludePathPatterns("/seller/sellerPasswordUpdateForm/**")
                .excludePathPatterns("/seller/join-form/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/post/list/**")
                .excludePathPatterns("/post/read/**")
                .excludePathPatterns("/order_list/**");


        registry.addInterceptor(new SellerLoginInterceptor())
                //seller 가 로그인이 되어있으면 add는들어갈수있는거 exclude 로그인이안되어있을때
                .order(1)
                .addPathPatterns("/product/detail/**")
                .addPathPatterns("/product/delete/**")
                .addPathPatterns("/product/registration/**")
                .addPathPatterns("/product/seller/**")
                .addPathPatterns("/product/update/**")
                .addPathPatterns("/seller/delete/**")
                .addPathPatterns("/seller/update/**")
                .addPathPatterns("/shop/join/**")
                .addPathPatterns("/shop/delete/**")
                .addPathPatterns("/shop/list/**")
                .addPathPatterns("/shop/update/**");
    }
}