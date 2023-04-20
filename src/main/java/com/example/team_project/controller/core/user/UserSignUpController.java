package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.service.auth.UserSignUpService;
import com.example.team_project.domain.domain.user.service.auth.dto.UserSignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/signup")
@RequiredArgsConstructor
public class UserSignUpController {

    private final UserSignUpService userSignUpService;

    @GetMapping("")
    public String signupForm(Model model) {
        model.addAttribute("userSignUpDTO", new UserSignUpDTO());
        return "thymeleaf/user/signupForm";
    }

    @PostMapping("")
    public String signup(@ModelAttribute("userSignUpDTO") UserSignUpDTO userSignUpDTO,
                         RedirectAttributes redirectAttributes) {
        userSignUpService.signUp(userSignUpDTO);
        return "redirect:/main";
    }

}
