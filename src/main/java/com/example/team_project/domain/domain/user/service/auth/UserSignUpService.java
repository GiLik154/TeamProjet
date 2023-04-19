package com.example.team_project.domain.domain.user.service.auth;

import com.example.team_project.domain.domain.user.domain.User;

public interface UserSignUpService {
    User signUpUser(String userId, String password, String userName, String phoneNumber, String zipcode, String streetAddress, String detailedAddress);
}
