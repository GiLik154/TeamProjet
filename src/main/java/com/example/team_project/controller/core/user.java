package com.example.team_project.controller.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/login")
public class user {

    @GetMapping("")
    public String loginform(){
        return "thymeleaf/user/loginForm";
    }

}
