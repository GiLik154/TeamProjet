package com.example.team_project.controller.core;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.login.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("user/login")
@SessionAttributes("userId")
public class userLoginController {

    private final UserLogin userLogin;
    private final UserRepository userRepository;

    @GetMapping("")
    public String loginForm(){
        System.out.println("이동");
        return "thymeleaf/user/loginForm";
    }

    @PostMapping("")
    public String login(@RequestParam("userId") Long userId, Model model){
        model.addAttribute("userId", userId);

        return "redirect:/main";
    }
}
