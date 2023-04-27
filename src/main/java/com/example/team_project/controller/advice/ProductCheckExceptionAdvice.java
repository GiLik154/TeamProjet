package com.example.team_project.controller.advice;


import com.example.team_project.controller.core.product.ProductRegistrationController;
import com.example.team_project.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ProductCheckExceptionAdvice {

    @ExceptionHandler({NotPasswordException.class})
    public ModelAndView passwordCheck(NotPasswordException ex) {
        String errorMessage = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler({SellerNotFoundException.class})
    public ModelAndView idCheck(SellerNotFoundException ex) {
        String errorMessage = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler({SellerDuplicateSellerException.class})
    public ModelAndView idCheck(SellerDuplicateSellerException ex) {
        String errorMessage = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler({TokenNotFoundException.class})
    public ModelAndView tokenCheck(TokenNotFoundException ex) {
        String errorMessage = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/seller/sellerEmailInputForm");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ModelAndView product(ProductNotFoundException ex) {
        String errorMessage = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }
}
