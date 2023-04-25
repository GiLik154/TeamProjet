package com.example.team_project.domain.domain.user.service.auth;


import com.example.team_project.domain.domain.user.dto.UserSignUpDto;
import org.springframework.validation.Errors;

import java.util.Map;


public interface UserSignUpService {
    Map<String, String> validateHandler(Errors errors);

    boolean isUserIdDuplicated(String userId);

    boolean isEmailDuplicated(String email);

    boolean isPhoneNumberDuplicated(String phoneNumber);

    static class UidExistException extends Exception { }

    void signUp(UserSignUpDto userSignUpDto) throws Exception ;

}

