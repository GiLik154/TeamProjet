package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.login.UserLogin;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/login")
@SessionAttributes("userId")
public class UserLoginController {

    private final UserLogin userLogin;
    private final UserRepository userRepository;

    @GetMapping("")
    public String loginForm() {
        return "thymeleaf/user/loginForm";
    }

    @PostMapping("")
    public String login(@RequestParam("userId") String userId, Model model) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        userLogin.userLogin(user.getId());
        model.addAttribute("userId", user.getId());

        return "thymeleaf/user/loginForm";
    }
}
