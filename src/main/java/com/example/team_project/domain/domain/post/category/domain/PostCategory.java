package com.example.team_project.domain.domain.post.category.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class PostCategory {
    @Id
    private String name;

    public PostCategory(){}

    public PostCategory(String name){
        this.name=name;
    }
}
