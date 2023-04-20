package com.example.team_project.security.dto;

import com.example.team_project.enums.UserGrade;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@ToString
public class UserSecurityDTO extends User {

    private String userId;

    private String password;

    private String userName;

    private String email;

    private String phoneNumber;

    private UserGrade userGrade;

    private boolean del;

    private boolean social;

    public UserSecurityDTO(String username, String password, String userName, String email,
                           String phoneNumber, UserGrade userGrade, boolean del, boolean social,
                           Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.userId = username;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userGrade = userGrade;
        this.del = del;
        this.social = social;
    }
}
