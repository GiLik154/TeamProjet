package com.example.team_project.domain.domain.review.base.service.dto;

import lombok.Getter;

@Getter
public class ReviewDto {
    private final String title;
    private final String content;
    private final String kinds;
    private final Long kindsId;

    public ReviewDto(String title,String content,String kinds,Long kindsId){
        this.title=title;
        this.content=content;
        this.kinds=kinds;
        this.kindsId=kindsId;
    }
}
