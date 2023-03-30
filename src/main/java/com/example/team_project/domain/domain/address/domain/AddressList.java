package com.example.team_project.domain.domain.address.domain;

import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

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

    protected AddressList() {
    }

    public AddressList(User user, String name, String recipientName, String recipientPhone, String recipientAddress) {
        this.user = user;
        this.name = name;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientAddress = recipientAddress;
    }

}
