package com.zerobase.musinsamonitor.service;

import static com.zerobase.musinsamonitor.exception.ErrorCode.EMAIL_ALREADY_USED;
import static com.zerobase.musinsamonitor.exception.ErrorCode.EMAIL_NOT_FOUND;
import static com.zerobase.musinsamonitor.exception.ErrorCode.PASSWORD_DO_NOT_MATCH;

import com.zerobase.musinsamonitor.exception.CustomException;
import com.zerobase.musinsamonitor.model.Auth;
import com.zerobase.musinsamonitor.model.MemberDto;
import com.zerobase.musinsamonitor.model.Token;
import com.zerobase.musinsamonitor.repository.MemberRepository;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.security.jwt.TokenProvider;
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
            throw new CustomException(EMAIL_ALREADY_USED);
        }

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
            .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));

        if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(PASSWORD_DO_NOT_MATCH);
        }

        return this.tokenProvider.generateToken(user.getEmail(), user.getRoles());
    }
}
