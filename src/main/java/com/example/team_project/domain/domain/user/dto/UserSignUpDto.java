package com.example.team_project.domain.domain.user.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class UserSignUpDto {

    @NotNull(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotNull(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String userName;

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호는 000-0000-0000 형식으로 입력해주세요.")
    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNumber;

}