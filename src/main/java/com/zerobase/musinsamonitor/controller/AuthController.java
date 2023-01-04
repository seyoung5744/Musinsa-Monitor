package com.zerobase.musinsamonitor.controller;

import com.zerobase.musinsamonitor.model.Auth;
import com.zerobase.musinsamonitor.model.MemberDto;
import com.zerobase.musinsamonitor.model.Token;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.security.jwt.TokenProvider;
import com.zerobase.musinsamonitor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    /**
     * 회원가입을 위한 API
     */
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody Auth.SignUp request) {
        MemberDto result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    /**
     * 로그인을 위한 API
     */
    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody Auth.SignIn request) {
        Token token = this.memberService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}
