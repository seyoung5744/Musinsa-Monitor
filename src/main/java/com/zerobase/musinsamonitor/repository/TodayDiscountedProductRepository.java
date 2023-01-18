package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entity.TodayDiscountedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodayDiscountedProductRepository extends JpaRepository<TodayDiscountedProduct, Integer> {

}
