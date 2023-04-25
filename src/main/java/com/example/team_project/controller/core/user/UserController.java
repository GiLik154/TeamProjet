package com.example.team_project.controller.core.user;

import com.example.team_project.controller.core.user.valid.UserSignUpValidator;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import com.example.team_project.domain.domain.user.service.auth.UserSignUpService;
import com.example.team_project.exception.UserInfoAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

        userSignUpValidator.validate(userSignUpDto, bindingResult);

        if (bindingResult.hasErrors()) {
            // 유효성 검사 오류가 발생한 경우
            model.addAttribute("userSignUpDto", userSignUpDto);
            Map<String, String> validateMap = userSignUpService.validateHandler(bindingResult);

            for (String key : validateMap.keySet()) {
                model.addAttribute(key, validateMap.get(key)); // Model 객체에 오류 메시지 추가
            }

            return "thymeleaf/user/signForm";

        } else {
            // 유효성 검사 오류가 없는 경우
            if (userSignUpService.isUserIdDuplicated(userSignUpDto.getUserId())) {
                // 사용자 아이디 중복 체크
                model.addAttribute("userSignUpDto", userSignUpDto);
                model.addAttribute("valid_userId", "이미 사용 중인 아이디입니다.");
                return "thymeleaf/user/signForm";
            } else if (userSignUpService.isEmailDuplicated(userSignUpDto.getEmail())) {
                // 이메일 중복 체크
                model.addAttribute("userSignUpDto", userSignUpDto);
                model.addAttribute("valid_email", "이미 사용 중인 이메일입니다.");
                return "thymeleaf/user/signForm";
            } else if (userSignUpService.isPhoneNumberDuplicated(userSignUpDto.getPhoneNumber())) {
                // 전화번호 중복 체크
                model.addAttribute("userSignUpDto", userSignUpDto);
                model.addAttribute("valid_phoneNumber", "이미 사용 중인 전화번호입니다.");
                return "thymeleaf/user/signForm";
            }
            System.out.println("11111");
            userSignUpService.signUp(userSignUpDto);
            return "redirect:/";

        }
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
