package com.example.jpa_learn.entity;

import com.example.jpa_learn.entity.enums.MFAType;
import com.example.jpa_learn.utils.DateUtils;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Table(name="user")
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "username", length = 50, nullable = false)
    String username;

    @Column(name = "nickname", nullable = false)
    private String nickname;


    @Column(name="password", nullable = false)
    String password;

    @Column(name = "email", length = 128)
    String email;

    @Column(name = "expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @Column(name = "mfa_type", nullable = false)
    @ColumnDefault("0")
    private MFAType mfaType;

    @PrePersist
    public void prePersist() {

        if (email == null) {
            email = "";
        }
        if (expireTime == null) {
            expireTime = DateUtils.now();
        }
    }


}
