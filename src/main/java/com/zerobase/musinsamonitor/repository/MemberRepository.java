package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByUsername(String username);
    Optional<MemberEntity> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
