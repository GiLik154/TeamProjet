package com.example.team_project.controller.advice;

import com.example.team_project.exception.PostNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class PostExceptionAdvice {
    public static String ERROR_PAGE = "thymeleaf/error/error-page";

    @ExceptionHandler(PostNotFoundException.class)
    public ModelAndView notFoundPost(PostNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }
}
