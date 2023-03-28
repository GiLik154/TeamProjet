package com.example.team_project.domain.postcategory.service.add;

import com.example.team_project.domain.postcategory.domain.PostCategory;
import com.example.team_project.domain.postcategory.domain.PostCategoryRepository;
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
        PostCategory postCategory = new PostCategory(name);
        postCategoryRepository.save(postCategory);
    }
}
