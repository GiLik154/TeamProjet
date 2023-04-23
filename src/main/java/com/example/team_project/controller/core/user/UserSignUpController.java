package com.example.team_project.controller.core.user;


import com.example.team_project.controller.core.user.dto.UserJoinDTO;
import com.example.team_project.domain.domain.user.service.auth.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user/signup")
@RequiredArgsConstructor
public class UserSignUpController {

        private final UserSignUpService userSignUpService;

        @GetMapping("")
        public String joinForm() {
                return "thymeleaf/user/signupForm";
        }

        @PostMapping("")
        public String join(UserJoinDTO userJoinDTO) throws UserSignUpService.UidExistException {
                String userId = userSignUpService.join(userJoinDTO);

                return "thymeleaf/main/main";
        }
}