package com.example.team_project.domain.domain.authentication.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class ConfirmationToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 1L;    //토큰 만료 시간


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime sendTime;

    private String ownerId;

    private String token;

    private LocalDateTime expireTime; // 토큰 만료 시간 필드 추가


    public ConfirmationToken(String ownerId, LocalDateTime sendTime, StringBuilder token) {
        this.ownerId = ownerId;
        this.sendTime = sendTime;
        this.token = String.valueOf(token);
        this.expireTime = sendTime.plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 현재 시간에 5분을 더해 만료 시간 설정
    }

    public ConfirmationToken() {

    }
}