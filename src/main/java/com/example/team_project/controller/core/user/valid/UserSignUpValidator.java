package com.example.team_project.controller.core.user.valid;

import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserSignUpValidator implements Validator {

    private static final String EMAIL_PATTERN = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$";
    private static final String PHONE_NUMBER_PATTERN = "\\d{3}-\\d{4}-\\d{4}";

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSignUpDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserSignUpDto userSignUpDto = (UserSignUpDto) target;

        validateField("userId", userSignUpDto.getUserId(), 6, 20, errors);
        validateField("password", userSignUpDto.getPassword(), 8, 16, errors);
        validateField("userName", userSignUpDto.getUserName(), 2, 10, errors);
        validateField("email", userSignUpDto.getEmail(), EMAIL_PATTERN, errors);
        validateField("phoneNumber", userSignUpDto.getPhoneNumber(), PHONE_NUMBER_PATTERN, errors);
    }

    private void validateField(String fieldName, String fieldValue, int minLength, int maxLength, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty");
        if (fieldValue.length() < minLength || fieldValue.length() > maxLength) {
            errors.rejectValue(fieldName, "Size.userSignUpDto." + fieldName);
        }
    }

    private void validateField(String fieldName, String fieldValue, String pattern, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "NotEmpty");
        if (!fieldValue.matches(pattern)) {
            errors.rejectValue(fieldName, "Pattern.userSignUpDto." + fieldName);
        }
    }
}