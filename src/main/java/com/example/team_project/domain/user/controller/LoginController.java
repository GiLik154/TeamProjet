package com.example.team_project.domain.user.controller;

import com.example.team_project.domain.user.dto.PostDto;
import com.example.team_project.domain.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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
    public String loginForm(){
        return "thymeleaf/login";
    }


    @PostMapping("/login")
    public String loginCheck(PostDto postDto){
      boolean result =   loginService.login(postDto);
        if(result){
            return "thymeleaf/index";
        }else{
            throw new DuplicateKeyException("아이디 또는 비밀번호가 틀림");
        }

    }



}
