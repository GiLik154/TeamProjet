package com.example.team_project.domain.domain.user.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
abstract class BaseTimeEntity {

    @Column(name = "created_date", nullable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date", nullable = false)
    @LastModifiedDate
    private String modifiedDate;

    /**
     *엔티티를 저장하기 전에 실행
     */
    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.modifiedDate = this.createdDate;
    }

    /**
     * 엔티티를 업데이트 하기 전에 실행
     */
    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

}
