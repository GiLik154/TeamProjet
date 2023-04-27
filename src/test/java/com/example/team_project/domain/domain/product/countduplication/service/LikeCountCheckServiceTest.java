package com.example.team_project.domain.domain.product.countduplication.service;

import com.example.team_project.domain.domain.product.countduplication.domain.LikeCountRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikeCountCheckServiceTest {

    private final UserRepository userRepository;
    private final LikeCountRepository likeCountRepository;
    private final LikeCountCheckService likeCountCheckService;
    private final ProductRepository productRepository;

    @Autowired
    public LikeCountCheckServiceTest(UserRepository userRepository, LikeCountRepository likeCountRepository, LikeCountCheckService likeCountCheckService, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.likeCountRepository = likeCountRepository;
        this.likeCountCheckService = likeCountCheckService;
        this.productRepository = productRepository;
    }

    @Test
    void 좋아요_증가_성공() {
        //user 회원가입
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        Product product = new Product("productTest", null, "imageTest", 1, 10, null);
        productRepository.save(product);

        // 상품 좋아요 증가

        boolean ch = likeCountCheckService.countCheck(user.getId(), product.getId());
        boolean ch2 = likeCountCheckService.countCheck(user.getId(), product.getId());

        // 검증
        assertTrue(ch);
        assertFalse(ch2);


    }

}