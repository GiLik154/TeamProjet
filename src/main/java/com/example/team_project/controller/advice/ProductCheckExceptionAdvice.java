package com.example.team_project.controller.advice;


import com.example.team_project.exception.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
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

    //테스트
    @ExceptionHandler({MinMaxException.class})
    public ModelAndView MinMaxCheck(MinMaxException ex) {
        String errorMessage = ex.getMessage();
        ModelAndView modelAndView = new ModelAndView("thymeleaf/error/error-page");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors()
//                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
//        return ResponseEntity.badRequest().body(errors);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
