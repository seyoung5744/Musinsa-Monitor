package com.zerobase.musinsamonitor.service;

import static com.zerobase.musinsamonitor.exception.ErrorCode.EMAIL_NOT_FOUND;
import static com.zerobase.musinsamonitor.exception.ErrorCode.PASSWORD_DO_NOT_MATCH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zerobase.musinsamonitor.exception.CustomException;
import com.zerobase.musinsamonitor.model.Auth;
import com.zerobase.musinsamonitor.model.Auth.SignIn;
import com.zerobase.musinsamonitor.model.MemberDto;
import com.zerobase.musinsamonitor.model.Token;
import com.zerobase.musinsamonitor.repository.MemberRepository;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.security.jwt.TokenProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;


    @Test
    void passwordEncoder() {
        //given
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);

        //when
        when(passwordEncoder.encode(anyString()))
            .thenReturn("안녕하세용");

        System.out.println(passwordEncoder.encode(anyString()));
        //then
        assertEquals("안녕하세용", passwordEncoder.encode(anyString()));
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        Auth.SignUp request = Auth.SignUp.builder()
            .email("test1@naver.com")
            .username("test")
            .password("123")
            .roles(roles)
            .build();

        given(memberRepository.existsByEmail(anyString()))
            .willReturn(false);

        given(memberRepository.save(any()))
            .willReturn(MemberEntity.builder()
                .email("test1@naver.com")
                .username("test")
                .build());

        // save에서 저장되는 실제 MemberEntity는 captor 안으로 들어감.
        ArgumentCaptor<MemberEntity> captor = ArgumentCaptor.forClass(MemberEntity.class);

        //when
        MemberDto register = memberService.register(request);
        System.out.println(register);

        //then
        verify(memberRepository, times(1)).save(captor.capture());
    }

    // https://cantcoding.tistory.com/69

    @Test
    @DisplayName("실패-이미 존재하는 이메일일 경우")
    void signup_EmailAlreadyExists() {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        Auth.SignUp request = Auth.SignUp.builder()
            .email("seyoung5744@naver.com")
            .username("won")
            .password("123")
            .roles(roles)
            .build();

        given(memberRepository.existsByEmail(anyString()))
            .willReturn(true);

        // when
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> memberService.register(request));

        System.out.println(exception.getMessage());
        //then
        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        //given
        String encodedPassword = passwordEncoder.encode("1234");

        MemberEntity entity = MemberEntity.builder()
            .username("won")
            .email("test1@naver.com")
            .password(encodedPassword)
            .roles(Collections.singletonList("ROLE_USER"))
            .build();

        SignIn request = SignIn.builder()
            .email("test1@naver.com")
            .password("1234")
            .build();

        Token token = new Token("test");

        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.of(entity));

        given(tokenProvider.generateToken(anyString(), anyList()))
            .willReturn(token);

        // when
        Token authenticate = memberService.authenticate(request);

        // then
        assertEquals(token, authenticate);
    }

    @Test
    @DisplayName("실패 - 가입되지 않은 이메일일 때")
    void login_fail_emailNotFound() {
        //given
        SignIn request = SignIn.builder()
            .email("test1@naver.com")
            .password("1234")
            .build();

        given(memberRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        //when
        CustomException customException = assertThrows(CustomException.class,
            () -> memberService.authenticate(request));

        //then
        assertEquals(EMAIL_NOT_FOUND, customException.getErrorCode());
    }

    @Test
    @DisplayName("실패 - 비밀번호가 틀렸을 때")
    void login_fail_password_do_not_match() {
        //given
        SignIn request = SignIn.builder()
            .email("test1@naver.com")
            .password("1234")
            .build();

        String encodedPassword = passwordEncoder.encode("1234");
        MemberEntity entity = MemberEntity.builder()
            .username("won")
            .email("test1@naver.com")
            .password(encodedPassword)
            .roles(Collections.singletonList("ROLE_USER"))
            .build();

        given(memberRepository.findByEmail(request.getEmail()))
            .willReturn(Optional.of(entity));

        given(passwordEncoder.matches(request.getPassword(), entity.getPassword()))
            .willReturn(false);
        //when

        CustomException customException = assertThrows(CustomException.class,
            () -> memberService.authenticate(request));

        //then
        assertEquals(PASSWORD_DO_NOT_MATCH, customException.getErrorCode());
    }
}