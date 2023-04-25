package com.example.team_project.domain.domain.user.domain.rememberme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.Timestamp;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Id
    @Column(name = "series", length = 64)
    private String series;

    @Column(name = "token", length = 64, nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Timestamp lastUsed;


    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;
}
