package com.example.team_project.domain.domain.shop.seller.domain;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //판매자 아이디
    @Column
    private String ownerId;

    //비밀번호
    @Column
    private String password;

    //판매자 이름
    @Column
    private String ownerName;

    //판매자 핸드폰 번호
    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Shop> shops = new ArrayList<>();




    public Seller(String ownerId, String password, String ownerName, String phoneNumber) {
        this.ownerId = ownerId;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }


    protected Seller() {
    }

    public Seller(String ownerId, String password) {
        this.ownerId = ownerId;
        this.password = password;
    }


    public boolean isEncodePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
        return passwordEncoder.matches(password, this.password);
    }


    public boolean isValidPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }




    public void update(String ownerName, String phoneNumber) {
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }
}