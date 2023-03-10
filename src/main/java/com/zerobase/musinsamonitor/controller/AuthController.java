package com.zerobase.musinsamonitor.controller;

import com.zerobase.musinsamonitor.model.Auth;
import com.zerobase.musinsamonitor.model.MemberDto;
import com.zerobase.musinsamonitor.model.Token;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.security.jwt.TokenProvider;
import com.zerobase.musinsamonitor.service.MemberService;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 회원가입을 위한 API
     */
    @ApiOperation("회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@RequestBody Auth.SignUp request) {
        MemberDto result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    /**
     * 로그인을 위한 API
     */
    @ApiOperation("로그인")
    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@RequestBody Auth.SignIn request) {
        Token token = this.memberService.authenticate(request);
        return ResponseEntity.ok(token);
    }
}
