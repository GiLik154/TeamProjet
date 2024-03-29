package com.example.team_project.domain.domain.post.category.domain;

import com.example.team_project.enums.PostCategoryStatus;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class PostCategory {
    /**
     * 카테고리의 종류 이름
     */
    @Id
    private PostCategoryStatus name;

    protected PostCategory() {
    }

    public PostCategory(PostCategoryStatus name) {
        this.name = name;
    }

}
