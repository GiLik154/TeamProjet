package com.example.team_project.domain.domain.authentication.service.dto;


import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class MailDto {

    private LocalDateTime sendTime;
    private String ownerId;
    private String token;

    public MailDto(String ownerId) {
        this.ownerId = ownerId;
    }
}
