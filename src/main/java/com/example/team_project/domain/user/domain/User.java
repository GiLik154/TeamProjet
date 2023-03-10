package com.example.team_project.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 아이디 : test123
    private String password;

    public User() {
    }

    public User(String name,String password){
        this.name=name;
        this.password=password;
    }
}