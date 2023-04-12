package com.example.team_project.controller.advice;


import com.example.team_project.exception.SellerNotFoundException;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound(UserNotFoundException e) {

        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage",e.getMessage());
        return modelAndView;
    }
}
