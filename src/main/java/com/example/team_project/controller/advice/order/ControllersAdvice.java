package com.example.team_project.controller.advice.order;

import com.example.team_project.exception.CannotCancelOrderException;
import com.example.team_project.exception.OrderListNotFoundException;
import com.example.team_project.exception.OrderNotFoundException;
import com.example.team_project.exception.OutOfStockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllersAdvice {
    public static String ERROR_PAGE = "thymeleaf/error/error-page";


    @ExceptionHandler(OrderListNotFoundException.class)
    public ModelAndView notValidateOrderList(OrderListNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView notValidateOrder(OrderNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(OutOfStockException.class)
    public ModelAndView notExistedStock(OutOfStockException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }
    @ExceptionHandler(CannotCancelOrderException.class)
    public ModelAndView notExistedStock(CannotCancelOrderException exception) {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }
}




