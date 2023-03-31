package com.example.team_project.domain.domain.post.category.domain;

import com.example.team_project.enums.PostCategoryStatus;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class PostCategory {
    @Id
    private String name;

    protected PostCategory() {
    }

    public PostCategory(String name) {
        this.name = name;
    }
}
