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
    /**
     * 컨트롤단에서 게시글의 종류를 받아와서 저장함.
     */
    @Override
    public void add(String name){
        PostCategory postCategory = new PostCategory(getStatusName(name));
        postCategoryRepository.save(postCategory);
    }

    /**
     * 스트링의로 받은 게시글의 종류를 Enums value 로 변환.
     */
    private String getStatusName(String name){
        return PostCategoryStatus.valueOf(name).getName();
    }
}
