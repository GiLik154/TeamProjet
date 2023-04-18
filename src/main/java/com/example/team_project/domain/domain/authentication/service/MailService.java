package com.example.team_project.domain.domain.authentication.service;


import com.example.team_project.domain.domain.authentication.domain.ConfirmationToken;
import com.example.team_project.domain.domain.authentication.domain.ConfirmationTokenRepository;
import com.example.team_project.domain.domain.authentication.service.dto.MailDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class MailService {
    private final JavaMailSender mailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmailToken(String to, String subject, String ownerId) {

        Random random = new Random();

        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            token.append(random.nextInt(20));
        }

        String message = "당신의 토큰 입니다 : " + token.toString();
        //메일전송
        sendMail(to, subject, message);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                ownerId,
                LocalDateTime.now(),
                token);

        confirmationTokenRepository.save(confirmationToken);
    }


}






