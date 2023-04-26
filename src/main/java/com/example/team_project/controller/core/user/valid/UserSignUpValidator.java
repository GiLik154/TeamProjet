package com.example.team_project.controller.core.user.valid;

import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserSignUpValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserSignUpDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserSignUpDto userSignUpDto = (UserSignUpDto) target;

        // userId 필드 검사
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "NotEmpty");
        if (userSignUpDto.getUserId().length() < 6 || userSignUpDto.getUserId().length() > 20) {
            errors.rejectValue("userId", "Size.userSignUpDto.userId");
        }

        // password 필드 검사
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userSignUpDto.getPassword().length() < 8 || userSignUpDto.getPassword().length() > 16) {
            errors.rejectValue("password", "Size.userSignUpDto.password");
        }

        // userName 필드 검사
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty");
        if (userSignUpDto.getUserName().length() < 2 || userSignUpDto.getUserName().length() > 10) {
            errors.rejectValue("userName", "Size.userSignUpDto.userName");
        }

        // email 필드 검사
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!userSignUpDto.getEmail().matches("^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")) {
            errors.rejectValue("email", "Pattern.userSignUpDto.email");
        }

        // phoneNumber 필드 검사
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty");
        if (!userSignUpDto.getPhoneNumber().matches("\\d{3}-\\d{4}-\\d{4}")) {
            errors.rejectValue("phoneNumber", "Pattern.userSignUpDto.phoneNumber");
        }
    }
}
