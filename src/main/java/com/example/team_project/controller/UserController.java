package com.example.team_project.controller;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.domain.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final JoinService joinService;

    @GetMapping("/users/new")
    public String creatForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid UserForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "users/createUserForm";
        }

        User user = new User();
        user.setName(form.getName());
        user.setPassword(form.getPassword());

        joinService.join(user);
        return "/redirect:/";
    }
}
