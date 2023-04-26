package com.example.team_project.controller.core.user.mypage.view;

import com.example.team_project.controller.core.user.mypage.dto.UserFormDto;

import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
//
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/mypage")
public class myPageViewController {

    @GetMapping
    public String get(Model model, HttpSession session) {
        model.addAttribute("userFormDto", new UserFormDto());
        String sessionId = session.getId();
        return "thymeleaf/user/myPage";
    }

    @PostMapping
    public String post(Model model) {
        model.addAttribute("userSignUpDto", new UserSignUpDto());
        return "thymeleaf/user/signUp";
    }
}
