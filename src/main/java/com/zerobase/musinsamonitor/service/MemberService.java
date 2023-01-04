package com.zerobase.musinsamonitor.service;

import com.zerobase.musinsamonitor.model.Auth;
import com.zerobase.musinsamonitor.model.MemberDto;
import com.zerobase.musinsamonitor.model.Token;
import com.zerobase.musinsamonitor.repository.MemberRepository;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberDto register(Auth.SignUp request) {
        boolean exists = this.memberRepository.existsByEmail(request.getEmail());

        if (exists) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

//        request.setPassword(this.passwordEncoder.encode(request.getPassword()));

        String encodedPassword = this.passwordEncoder.encode(request.getPassword());

        return MemberDto.fromEntity(
            this.memberRepository.save(MemberEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encodedPassword)
                .roles(request.getRoles())
                .build())
        );
    }

    // password 인증
    public Token authenticate(Auth.SignIn request) {
        MemberEntity user = this.memberRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 Email 입니다."));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return this.tokenProvider.generateToken(user.getUsername(), user.getRoles());
    }
}
