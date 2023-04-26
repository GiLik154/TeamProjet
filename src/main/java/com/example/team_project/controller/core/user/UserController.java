package com.example.team_project.controller.core.user;

import com.example.team_project.controller.core.user.valid.UserSignUpValidator;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import com.example.team_project.domain.domain.user.service.auth.UserSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@SessionAttributes("userId")
public class UserController {

    private final UserSignUpService userSignUpService;
    private final UserSignUpValidator userSignUpValidator;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginGet() {
        return "thymeleaf/user/loginForm";
    }

    @PostMapping("/login")
    public String post(@RequestParam String userId, Model model) {
        model.addAttribute("userId", userId);
        return "thymeleaf/user/loginForm";
    }

    /**
     * 회원가입 요청 처리
     */
    @PostMapping("/signup")
    public String signUpPost(@Valid @ModelAttribute("userSignUpDto") UserSignUpDto userSignUpDto,
                             BindingResult bindingResult, Errors errors, Model model) throws Exception {

        if (userSignUpService.isUserIdDuplicated(userSignUpDto.getUserId())) {
            model.addAttribute("Valid_userId", "이미 사용 중인 아이디입니다.");
        }
        if (userSignUpService.isEmailDuplicated(userSignUpDto.getEmail())) {
            model.addAttribute("Valid_email", "이미 사용 중인 이메일입니다.");
        }
        if (userSignUpService.isPhoneNumberDuplicated(userSignUpDto.getPhoneNumber())) {
            model.addAttribute("Valid_phoneNumber", "이미 사용 중인 전화번호입니다.");
        }

        if (model.containsAttribute("Valid_userId") || model.containsAttribute("Valid_email") || model.containsAttribute("Valid_phoneNumber")) {
            model.addAttribute("userSignUpDto", userSignUpDto);
            return "thymeleaf/user/signForm";
        }

        if (errors.hasErrors()) {
            Map<String, String> validateResult = userSignUpService.validateHandler(errors);
            for (Map.Entry<String, String> entry : validateResult.entrySet()) {
                model.addAttribute("Valid_" + entry.getKey(), entry.getValue());
            }
            model.addAttribute("userSignUpDto", userSignUpDto);
            return "thymeleaf/user/signForm";
        }

        userSignUpValidator.validate(userSignUpDto, bindingResult);
        userSignUpService.signUp(userSignUpDto);

        return "redirect:/main";
    }

    /**
     * 회원가입 화면 요청
     */
    @GetMapping("/signup")
    public String SignUpGet(Model model) {
        model.addAttribute("userSignUpDto", new UserSignUpDto());
        return "thymeleaf/user/signForm";
    }
}