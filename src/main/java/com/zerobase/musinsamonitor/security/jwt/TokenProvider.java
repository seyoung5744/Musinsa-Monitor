package com.zerobase.musinsamonitor.security.jwt;

import com.zerobase.musinsamonitor.model.Token;
import com.zerobase.musinsamonitor.service.MemberDetailService;
import com.zerobase.musinsamonitor.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1000ms * 60s * 60m : 1 hour
    private static final String KEY_ROLES = "roles";

    private final MemberDetailService memberDetailService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /**
     * 토큰 생성(발급)
     *
     * @param username
     * @param roles
     * @return
     */
    public Token generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        // 토큰 생성 시간
        Date now = new Date();

        // 토큰 만료 시간 ( 생성 시간부터 1시간 )
        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now) // 토큰 생성 시간
            .setExpiration(expireDate) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS512, this.secretKey) // 사용할 암호화 알고리즘, 비밀키
            .compact();

        return new Token(token);
    }

    /**
     * jwt 로부터 인증 정보 가져오기
     */
    public Authentication getAuthorization(String jwt){
        UserDetails userDetails = this.memberDetailService.loadUserByUsername(this.getEmail(jwt));
        // 사용자 정보, 사용자 권한 정보를 포함한 token
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmail(String token){
        return this.parseClaims(token).getSubject();
    }

    public boolean isValidationToken(String token){
        if(!StringUtils.hasText(token)) return false;

        Claims claims = this.parseClaims(token);

        // 검사하려는 토큰이 갖고 있는 만료 시간이 현재 시간 이전인지 아닌지로 만료 여부 체크
        return !claims.getExpiration().before(new Date());
    }

    /**
     * 토큰 디코딩
     * @param token
     * @return
     */
    private Claims parseClaims(String token) {
        log.info("get All Claims token = {}", token);
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
