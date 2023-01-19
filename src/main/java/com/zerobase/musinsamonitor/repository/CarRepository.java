package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Cart, Long> {

}
