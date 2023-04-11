package com.example.team_project.controller.advice;


import com.example.team_project.exception.ShopIncorrectUpdatePasswordException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ShopUpdateAndDeleteExceptionAdvice {

    @ExceptionHandler({ShopIncorrectUpdatePasswordException.class, BadCredentialsException.class})
    public ModelAndView passwordCheck(Exception ex) {
        String errmsg = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/shop/shopUpdateForm");
        modelAndView.addObject("errMsg",errmsg);
        return modelAndView;
    }


}
