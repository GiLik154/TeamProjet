package com.example.team_project.domain.domain.authentication.service;

import com.example.team_project.domain.domain.authentication.domain.ConfirmationToken;
import com.example.team_project.domain.domain.authentication.domain.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Transactional
@ComponentScan
public class AutoDelete {
    private final JavaMailSender mailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Scheduled(fixedRate = 60000)
    public void deleteExpiredTokens() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(1);
        List<ConfirmationToken> expiredTokens = confirmationTokenRepository.findAllByExpireTimeBefore(expirationTime);

        if (!expiredTokens.isEmpty()) {
            confirmationTokenRepository.deleteAll(expiredTokens);
            System.out.println("Deleted " + expiredTokens.size() + " expired tokens from the database.");
        }
    }
}
