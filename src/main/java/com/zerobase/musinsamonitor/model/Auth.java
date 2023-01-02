package com.zerobase.musinsamonitor.model;

import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

public class Auth {

    @Data
    public static class SignIn {

        private String email;
        private String password;
    }

    @Data
    public static class SignUp {

        private String email;
        private String username;
        private String password;
        private List<String> roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .roles(this.roles)
                .createAt(LocalDateTime.now())
                .build();
        }
    }

}
