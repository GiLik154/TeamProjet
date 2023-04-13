package com.example.team_project.controller.advice;


import com.example.team_project.exception.NotPasswordException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ProductCheckExceptionAdvice {

    @ExceptionHandler({NotPasswordException.class})
    public ModelAndView passwordCheck(NotPasswordException ex) {
        String errmsg = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/password-check");
        modelAndView.addObject("errMsg",errmsg);
        return modelAndView;
    }

 
}
