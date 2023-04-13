package com.example.team_project.controller.advice.order;

import com.example.team_project.exception.OrderListNotFoundException;
import com.example.team_project.exception.OrderNotFoundException;
import com.example.team_project.exception.OutOfStockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllersAdvice {
    public static String ERROR_PAGE = "/thymeleaf/error/error-page";


    @ExceptionHandler(OrderListNotFoundException.class)
    public ModelAndView notValidateOrderList(OrderListNotFoundException exception) {

        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("errorMessage", exception.getMessage());

        return modelAndView;
    }
//todo 위처럼 수정중
    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView notValidateOrder() {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("Order_not_found_message", "해당 주문은 존재하지 않습니다");

        return modelAndView;
    }

    @ExceptionHandler(OutOfStockException.class)
    public ModelAndView notExistedStock() {
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject("out_of_stock", "해당 상품의 재고가 부족합니다");

        return modelAndView;

    }
}




