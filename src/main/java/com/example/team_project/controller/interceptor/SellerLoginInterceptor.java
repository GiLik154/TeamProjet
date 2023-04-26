package com.example.team_project.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class SellerLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        Optional<Object> checkUserId = Optional.ofNullable(session.getAttribute("sellerId"));

        System.out.println("인터셉터 작동은함?");

        if (checkUserId.isEmpty()) {
            session.setAttribute("isNotLogin", true);

            System.out.println("셀러");

            response.sendRedirect("/main");
            return false;
        }


        session.removeAttribute("isNotLogin");
        return true;
    }

}
