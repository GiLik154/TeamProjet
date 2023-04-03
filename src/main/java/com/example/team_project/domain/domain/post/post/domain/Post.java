package com.example.team_project.domain.domain.post.post.domain;

import com.example.team_project.domain.domain.image.ImageUpload;
import com.example.team_project.domain.domain.post.category.domain.PostCategory;
import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post implements ImageUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //고유키
    private Long id;
    //유저 고유키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    //게시글의 종류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;
    //게시글 제목
    private String title;
    //게시글 내용
    private String content;
    //이미지
    private String imagePath;
    //작성 시간
    private String time;
    //글 생성,삭제 여부
    private String situation;

    public Post(){}

    public Post(String title,String content, String time, User user, PostCategory postCategory){
        this.title=title;
        this.content=content;
        this.time=time;
        this.user=user;
        this.postCategory=postCategory;
        this.situation="create";
    }

    public void delete(){
        this.situation="delete";
    }
    public void update(String title,String content,String time,PostCategory postCategory){
        this.title=title;
        this.content=content;
        this.time=time;
        this.postCategory=postCategory;
    }

    @Override
    public void uploadImage(String imagePath){
        this.imagePath =imagePath;
    }
}
