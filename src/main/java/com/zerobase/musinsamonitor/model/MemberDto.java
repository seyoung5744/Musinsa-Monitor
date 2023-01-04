package com.zerobase.musinsamonitor.model;

import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String email;
    private String username;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static MemberDto fromEntity(MemberEntity memberEntity){
        return MemberDto.builder()
            .email(memberEntity.getEmail())
            .username(memberEntity.getUsername())
            .createdAt(memberEntity.getCreatedAt())
            .updatedAt(memberEntity.getUpdatedAt())
            .roles(memberEntity.getRoles())
            .build();
    }
}
