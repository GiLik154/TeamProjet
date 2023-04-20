package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.auth.UserSignUpService;
import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/signup")
@RequiredArgsConstructor
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    private final UserRepository userRepository;

    @GetMapping("")
    public void signupGET() {

    }

    @PostMapping("")
    public String signupPOST(UserSignUpDTO userSignUpDTO, RedirectAttributes redirectAttributes) {

        try {
            userSignUpService.signUp(userSignUpDTO);
        } catch (UserSignUpService.)
        return "redirect:/";
    }


}
