package com.example.team_project.domain.post.domain;

import com.example.team_project.domain.postcategory.domain.PostCategory;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //고유키
    private Long id;
    //유저 고유키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
    //게시글 내용
    private String content;
    //작성 시간
    private String time;
    //게시글의 종류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;
    //글 생성,삭제 여부
    private String situation;

    public Post(){}

    public Post(String content, String time, User user, PostCategory postCategory){
        this.content=content;
        this.time=time;
        this.user=user;
        this.postCategory=postCategory;
        this.situation="create";
    }

    public void delete(){
        this.situation="delete";
    }
    public void update(String content,String time,PostCategory postCategory){
        this.content=content;
        this.time=time;
        this.postCategory=postCategory;
    }
}
