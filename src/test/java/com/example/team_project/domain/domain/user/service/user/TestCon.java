package com.example.team_project.domain.domain.user.service.user;

import com.example.team_project.domain.domain.authentication.service.MailService;
import com.example.team_project.domain.domain.authentication.service.dto.MailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalTime.now;

@SpringBootTest
@Transactional
public class TestCon {


    private final MailService ms;


    @Autowired
    public TestCon(MailService ms) {
        this.ms = ms;
    }


//    @Test
//    public void sendMail(){
//        //ms.sendEmailToken("okk080200@naver.com","메일테스트","a");
//        MailDto mailDto = new MailDto("a","123123");
//
//        ms.sendEmailToken("chltpgns1@gmail.com","메일테스트","Asd");
//
//    }
}
