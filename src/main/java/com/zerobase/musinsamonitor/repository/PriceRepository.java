package com.zerobase.musinsamonitor.repository;

import com.zerobase.musinsamonitor.repository.entiry.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

}
