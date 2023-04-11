package com.example.team_project.controller.advice;


import com.example.team_project.exception.ExpiredCouponException;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CouponExceptionAdvice {

    @ExceptionHandler(ExpiredCouponException.class)
    public ModelAndView expiredCoupon(ExpiredCouponException e) {

        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage",e.getMessage());
        return modelAndView;
    }
}
