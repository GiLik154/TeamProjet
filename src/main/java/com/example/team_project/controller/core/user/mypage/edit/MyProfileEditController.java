package com.example.team_project.controller.core.user.mypage.edit;

import com.example.team_project.controller.core.user.mypage.dto.UserFormDto;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.service.user.UpdateUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/mypage/edit")
@RequiredArgsConstructor
public class MyProfileEditController {

    private final UserRepository userRepository;
    private final UpdateUserInfoService updateUserInfoService;

    @GetMapping("")
    public String userProfileEdit(@SessionAttribute("userId") Long userId, Model model) {
        User user = userRepository.findById(userId).orElseThrow();
        UserFormDto userFormDto = new UserFormDto(user.getId(), user.getPassword(), user.getUserName(), user.getPhoneNumber());
        model.addAttribute("userFormDto", userFormDto);
        return "thymeleaf/user/user_profile";
    }

    @PostMapping("")
    public String userProfileUpdate(@SessionAttribute("userId") Long userId, @ModelAttribute UserFormDto userFormDto) {
        updateUserInfoService.updateUserInfo(userFormDto.getId(), userFormDto.getPassword(), userFormDto.getUserName(), userFormDto.getPhoneNumber());
        return "redirect:/user/mypage";
    }
}
