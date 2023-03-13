package com.example.team_project.domain.user.controller;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.DuplicateFormatFlagsException;

@ControllerAdvice(assignableTypes = {JoinController.class, LoginController.class})
public class JoinControllerAdvice {

    @ExceptionHandler(DuplicateKeyException.class)
    public String handleDuplicateKeyException(DuplicateKeyException ex, Model model, HttpServletRequest request){
        String errMsg = ex.getMessage();
        String referer = request.getHeader("Referer");

        if(referer != null && referer.contains("login")){
            model.addAttribute("loginError", errMsg);
            return "thymeleaf/login";
        }else{
            model.addAttribute("joinError", errMsg);
            return "thymeleaf/joinuser";
        }

    }



}
