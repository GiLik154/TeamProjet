//package com.example.team_project.controller.core.user.mypage.edit;
//
//import com.example.team_project.controller.core.user.mypage.dto.UserFormDto;
//import com.example.team_project.domain.domain.post.post.domain.PostRepository;
//import com.example.team_project.domain.domain.post.post.service.PostDto;
//import com.example.team_project.domain.domain.post.post.service.update.PostUpdateService;
//import com.example.team_project.domain.domain.user.domain.UserRepository;
//import com.example.team_project.domain.domain.user.service.user.UpdateUserInfoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/user/mypage/edit")
//public class myProfileEditController {
//
//    private final UpdateUserInfoService updateUserInfoService;
//    private final UserRepository userRepository;
//
//    @GetMapping("")
//    public String get(@RequestParam("UserId") Long userId,
//                      Model model){
//
//        userRepository.findById(userId).ifPresent(post -> {
//            model.addAttribute("user", user);
//        });
//
//        return "thymeleaf/user/user_profile";
//    }
//
//    @PostMapping("")
//    public String post(@SessionAttribute("userId") Long userId,
//                       UserFormDto userFormDto){
//        UpdateUserInfoService.updateUserInfo(userFormDto.getUserName(), userFormDto.getPassword(), userFormDto.getPhoneNumber());
//        return "redirect:/user/mypage";
//    }
//}
