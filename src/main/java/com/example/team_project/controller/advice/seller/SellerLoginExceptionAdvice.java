package com.example.team_project.controller.advice.seller;


import com.example.team_project.exception.SellerNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerLoginExceptionAdvice {

    @ExceptionHandler({BadCredentialsException.class})
    public ModelAndView passwordCheck(BadCredentialsException ex) {
        String errmsg = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/seller/sellerLoginForm");
        modelAndView.addObject("errMsg",errmsg);
        return modelAndView;
    }


}
