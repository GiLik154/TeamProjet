package com.example.team_project.domain.domain.user.domain;
import com.example.team_project.enums.Role;
import com.example.team_project.enums.SocialType;
import com.example.team_project.enums.UserGrade;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    private String imageUrl;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGrade userGrade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    /**
     *유저 권한 설정 메소드
     */
    public void authorizeUser() {
        this.role = Role.USER;
    }

    /**
     * 비밀번호 암호화 메서드
     */
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    /**
     * 회원가입 시 유저의 정보를 담는 생성자
     */
    public User(String userId, String password, String userName, String email, String phoneNumber, Role role, UserGrade userGrade) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userGrade = UserGrade.SILVER;
        this.role = Role.USER;
    }

    public User(String password, String userName, String phoneNumber) {
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public User(String userId, String password, String userName, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * 입력 받은 비밀번호를 검증하는 메서드
     */
    public boolean isValidPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches((password), this.password);
    }

    /**
     * 유저의 정보를 변경하는 메서드들
     */
    public void modify(User updatedUser) {
        this.password = updatedUser.getPassword();
        this.userName = updatedUser.getUserName();
        this.phoneNumber = updatedUser.getPhoneNumber();
    }


    public void updateUserGrade(UserGrade userGrade) {
        this.userGrade = userGrade;
    }
}