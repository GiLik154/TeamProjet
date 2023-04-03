package com.example.team_project.domain.domain.post.post.service;

import lombok.Getter;

@Getter
public class PostDto {
    private final String title;
    private final String content;
    private final String category;

    public PostDto(String title,String content,String category){
        this.title=title;
        this.content=content;
        this.category=category;
    }
}
