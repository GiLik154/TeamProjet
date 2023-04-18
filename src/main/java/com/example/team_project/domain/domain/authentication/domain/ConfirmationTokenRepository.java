package com.example.team_project.domain.domain.authentication.domain;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.exception.TokenNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> deleteByToken(String token);

    Optional<ConfirmationToken> findByToken(String token);

    default void validate(String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = findByToken(token);


        if (!optionalConfirmationToken.isPresent()) {
            throw new TokenNotFoundException("토큰 번호를 다시 확인해주세요");
        }else{
            deleteByToken(token);
        }
    }

    List<ConfirmationToken> findAllByExpireTimeBefore(LocalDateTime expirationTime);
}
