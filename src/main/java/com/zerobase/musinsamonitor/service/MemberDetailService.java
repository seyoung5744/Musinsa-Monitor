package com.zerobase.musinsamonitor.service;

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
        // findByUsername는 Optional 값을 return하지만 orElseThrow을 사용했으므로 Optional이 벗겨진 MemberEntity반환
        return this.memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + email));
    }
}
