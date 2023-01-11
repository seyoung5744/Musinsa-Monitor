package com.zerobase.musinsamonitor.service;

import static com.zerobase.musinsamonitor.exception.ErrorCode.EMAIL_NOT_FOUND;

import com.zerobase.musinsamonitor.exception.CustomException;
import com.zerobase.musinsamonitor.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.memberRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
    }
}
