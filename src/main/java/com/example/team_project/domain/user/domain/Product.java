package com.example.team_project.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;


}
