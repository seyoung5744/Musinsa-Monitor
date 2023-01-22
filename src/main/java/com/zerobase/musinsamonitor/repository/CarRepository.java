package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entity.Cart;
import com.zerobase.musinsamonitor.repository.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CarRepository extends JpaRepository<Cart, Long> {

//    @Transactional
    void deleteByProductAndEmail(Product product, String email);

    boolean existsByProductAndEmail(Product product, String email);

    Page<Cart> findAllByEmail(String email, Pageable pageable);
}
