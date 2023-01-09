package com.zerobase.musinsamonitor.model;

import com.sun.istack.NotNull;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

public class Auth {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignIn {

        @NotNull
        private String email;

        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUp {
        private String email;
        private String username;
        private String password;
        private List<String> roles;
    }

}
