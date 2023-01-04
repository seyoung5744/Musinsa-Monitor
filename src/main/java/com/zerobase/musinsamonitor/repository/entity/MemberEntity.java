package com.zerobase.musinsamonitor.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Entity(name = "MEMBER")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity extends BaseEntity implements UserDetails {
    private String email;

    private String username;

    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    // 이메일 인증 여부
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;

    // 메일을 통해서 인증키를 보내주면 해당 인증키가 맞는지를 검사하기 위해 임의의 값을 저장하여 값 비교
    // 회원가입할 때 key를 만들어서 이메일로 보내주고 보내준 이메일 링크를 통해 인증 절차 진행
    private String emailAuthKey;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(SimpleGrantedAuthority::new) // Spring Security에서 제공해주는 ROLE 관련 기능을 사용하기 위해 mapping
            .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
