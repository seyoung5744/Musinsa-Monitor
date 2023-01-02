package com.zerobase.musinsamonitor.service;

import com.zerobase.musinsamonitor.model.Auth;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // findByUsername는 Optional 값을 return하지만 orElseThrow을 사용했으므로 Optional이 벗겨진 MemberEntity반환
        return this.memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + email));
    }

    public MemberEntity register(Auth.SignUp member) {
//        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        boolean exists = this.memberRepository.existsByEmail(member.getEmail());
        if (exists) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));

        return this.memberRepository.save(member.toEntity());
    }

    // password 인증
    public MemberEntity authenticate(Auth.SignIn member) {
        MemberEntity user = this.memberRepository.findByEmail(member.getEmail())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 Email 입니다."));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
