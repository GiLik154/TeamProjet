package com.example.team_project.domain.domain.post.post.domain;

import com.example.team_project.domain.domain.image.ImageUpload;
import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.PostCategoryStatus;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post implements ImageUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 게시글을 작성한 유저의 정보
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 게시글의 종류
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;
    /**
     * 게시글의 제목
     */
    private String title;
    /**
     * 게시글의 내용
     */
    private String content;
    /**
     * 게시글의 이미지
     */
    private String imagePath;
    /**
     * 게시글의 작성시간
     */
    private String time;
    /**
     * 게시글의 존재여부(생성,삭제)등
     */
    private String situation;
    protected Post(){
    }
    /**
     * 게시글 처음 작성시 컨트롤단의 값을 입력받아 생성
     */
    public Post(String title,String content, String time, User user, PostCategory postCategory){
        this.title=title;
        this.content=content;
        this.time=time;
        this.user=user;
        this.postCategory=postCategory;
        this.situation="create";
    }
    /**
     * 게시글의 존재여부를 삭제로 변경
     * 게시글을 완전히 삭제하지 않고 존재여부만 변결함
     */
    public void delete(){
        this.situation="delete";
    }

    /**
     * 게시글을 변경시 컨트롤단의 값을 입력받아 업데이트함
     */
    public void update(String title,String content,String time,PostCategory postCategory){
        this.title=title;
        this.content=content;
        this.time=time;
        this.postCategory=postCategory;
    }

    /**
     * 이미지의 주소를 저장
     */
    @Override
    public void uploadImage(String imagePath){
        this.imagePath =imagePath;
    }
}
