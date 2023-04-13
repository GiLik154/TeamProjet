package com.example.team_project.domain.domain.post.category.service.add;

public interface PostCategoryAddService {
    /**
     * 컨트롤단에서 게시글의 종류를 받아와서 저장함.
     * @param name 게시글의 종류
     */
    void add(String name);
}
