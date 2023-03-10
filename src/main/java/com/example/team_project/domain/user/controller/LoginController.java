package com.example.team_project.domain.user.controller;

import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teamlogin")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;


    @GetMapping("/login")
    public String loginform(){
        return "thymeleaf/login";
    }

    @PostMapping("/login")
    public String logincheck(PostDto postDto, Model model){
      boolean result =   loginService.login(postDto);
        if(result){
            return "thymeleaf/index";

        }else{
            model.addAttribute("error","아이디 또는 비밀번호가 틀림");
            return "thymeleaf/login";
        }

    }



}
