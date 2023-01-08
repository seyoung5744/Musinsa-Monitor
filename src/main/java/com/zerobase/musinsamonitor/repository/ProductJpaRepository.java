package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entiry.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
//    Page<String> findAllByBrand(Pageable pageable);
//    Page<ProductEntity> findByBrand(String brand, Pageable pageable);
    Optional<Product> findByProductId(int productId);
    boolean existsByProductId(int productId);

}
