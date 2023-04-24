package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import com.example.team_project.domain.domain.user.service.auth.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserSignUpService userSignUpService;

    @GetMapping("/login")
    public String loginGet() {
        return "thymeleaf/user/loginForm";
    }

    @PostMapping("/signup")
    public String signUpPost(@ModelAttribute UserSignUpDto userSignUpDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
        if (bindingResult.hasErrors()) {
            return "thymeleaf/user/signupForm";
        }
        userSignUpService.signUp(userSignUpDto);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String SignUpGet() {
        return "thymeleaf/user/signupForm";
    }
}
