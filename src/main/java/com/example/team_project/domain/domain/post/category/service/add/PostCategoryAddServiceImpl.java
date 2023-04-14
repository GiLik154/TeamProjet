package com.example.team_project.domain.domain.post.category.service.add;

import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.post.category.domain.PostCategoryRepository;
import com.example.team_project.enums.PostCategoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCategoryAddServiceImpl implements PostCategoryAddService{
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public void add(String name){
        PostCategory postCategory = new PostCategory(getStatusName(name));
        postCategoryRepository.save(postCategory);
    }

    private String getStatusName(String name){
        return PostCategoryStatus.valueOf(name).getName();
    }
}
