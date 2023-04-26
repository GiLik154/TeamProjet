package com.example.team_project.controller.core.user;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/join")
public class UserJoinController {

    private final UserService userService;

    @GetMapping
    public ModelAndView joinForm() {

        return new ModelAndView("thymeleaf/user/joinform");
    }

    @PostMapping
    public ModelAndView joinForm(UserJoinControllerDto userJoinControllerDto) {

        ModelAndView modelAndView = new ModelAndView("redirect:/user/login");
        User user = userService.createUser(userJoinControllerDto.getUserId(),
                userJoinControllerDto.getPassword(),
                userJoinControllerDto.getUserName(),
                userJoinControllerDto.getPhoneNumber());
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
