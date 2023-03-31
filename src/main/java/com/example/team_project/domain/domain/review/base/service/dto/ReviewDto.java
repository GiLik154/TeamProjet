package com.example.team_project.domain.domain.review.base.service.dto;

import lombok.Getter;

@Getter
public class ReviewDto {
    private String title;
    private String content;
    //리뷰 이미지
    private String kinds;
    private Long kindsId;

    public ReviewDto(){
    }
    public ReviewDto(String title,String content,String kinds,Long kindsId){
        this.title=title;
        this.content=content;
        this.kinds=kinds;
        this.kindsId=kindsId;
    }
}
