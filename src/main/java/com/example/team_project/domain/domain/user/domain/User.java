package com.example.team_project.domain.domain.user.domain;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.enums.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAddress> userAddresses;

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderList> orderLists;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGrade userGrade;

    protected User() {}

    /**
     * 회원가입 시 유저의 정보를 담는 생성자
     */
    public User(String userId, String password, String name, String phoneNumber, UserGrade userGrade) {
        this.userId = userId;
        this.password = password;
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.userGrade = userGrade;
    }

    /**
     * 유저 정보 변경 시
     * 기존의 유저를 삭제하거나, 추가적인 유저를 생성하지 않고
     * 유저 정보만 변경하기 위한 생성자
     */
    public User(Long id, String userId, String password, String name, String phoneNumber, UserGrade userGrade) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = name;
        this.phoneNumber = phoneNumber;
        this.userGrade = userGrade;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public User(String userId, String password, String userName, String phoneNumber, UserGrade userGrade, PasswordEncoder passwordEncoder) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userGrade = userGrade;
        encodePassword(passwordEncoder);
    }

    public void updateUserGrade(UserGrade userGrade) {
        this.userGrade = userGrade;
    }
}