package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.auth.UserLoginService;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping("/user/login")
@SessionAttributes("userId")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @GetMapping("")
    public String loginForm() {
        return "thymeleaf/user/loginForm";
    }

    @PostMapping("")
    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password, Model model) {

        model.addAttribute("userId", userLoginService.login(userId, password));

        return "redirect:/main";
    }
}
