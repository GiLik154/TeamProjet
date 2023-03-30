package com.example.team_project.domain.domain.address.domain;

import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AddressList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String name;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
}
