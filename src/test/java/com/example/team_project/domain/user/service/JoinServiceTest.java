package com.example.team_project.domain.user.service;

import com.example.team_project.domain.user.domain.User;
import com.example.team_project.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JoinServiceTest {
    @Autowired
    JoinService joinService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;
    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setName("test");

        //when
        Long saveId = joinService.join(user);

        //then
        assertEquals(user, userRepository.findOne(saveId));
    }


    @Test(expected=IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        User user1 = new User();
        user1.setName("kim");

        User user2 = new User();
        user2.setName("kim");

        //when
        joinService.join(user1);
        joinService.join(user2);

        //then
        fail("예외가 발생해야 한다.");
    }
}