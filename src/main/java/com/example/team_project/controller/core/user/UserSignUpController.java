package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/signup")
@RequiredArgsConstructor
public class UserSignUpController {
    private final UserRepository userRepository;

    @PostMapping("")
    public String signupPOST(UserSignUpDTO userSignUpDTO) {
        return "redirect:/";
    }


}
