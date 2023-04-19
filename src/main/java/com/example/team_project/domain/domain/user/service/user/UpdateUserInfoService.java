package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;

public interface UpdateUserInfoService {
    User updateUserInfo(String password, String userName, String phoneNumber);
}
