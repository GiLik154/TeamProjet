package com.example.team_project.controller.advice;

import com.example.team_project.exception.BaseReviewAndUserNotFoundException;
import com.example.team_project.exception.BaseReviewNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class ReviewExceptionAdvice {
    public static String ERROR_PAGE = "thymeleaf/error/error-page";

    @ExceptionHandler(BaseReviewAndUserNotFoundException.class)
    public ModelAndView notFoundBaseReviewAndUser(BaseReviewAndUserNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(BaseReviewNotFoundException.class)
    public ModelAndView notFoundBaseReview(BaseReviewNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }
}
