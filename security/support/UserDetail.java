package com.example.jpa_learn.security.support;

import com.example.jpa_learn.entity.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    private User user;

    @NonNull
    public User getUser(){return user;}

    @NonNull
    public void setUser(User user){this.user = user;}
}
