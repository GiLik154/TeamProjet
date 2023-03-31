package com.example.team_project.domain.domain.review.base.service.dto;

import lombok.Getter;

@Getter
public class ReviewDto {
    private String content;
    //리뷰 이미지
    private String imagePath;
    private String kinds;
    private Long kindsId;

    public ReviewDto(){
    }
    public ReviewDto(String content,String imagePath,String kinds,Long kindsId){
        this.content=content;
        this.imagePath=imagePath;
        this.kinds=kinds;
        this.kindsId=kindsId;
    }
}
