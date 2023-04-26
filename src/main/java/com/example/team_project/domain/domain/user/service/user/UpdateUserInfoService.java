package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.user.domain.User;

import java.util.Optional;

public interface UpdateUserInfoService {

    /**
     * 유저 Id로 해당 유저의 존재를 확인한다.
     * 존재하는 경우 유저의 정보를 변경한다.
     * @param userId 유저 아이디
     * @param password 유저 패스워드
     * @param userName 유저 이름
     * @param PhoneNumber 유저 휴대폰 번호
     */
    public void updateUserInfo(Long userId, String password, String userName, String PhoneNumber);

}
