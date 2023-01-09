package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entiry.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    boolean existsByBrand(String brandName);
}
