package com.zerobase.musinsamonitor.repository.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity(name = "DISCOUNT")
@Getter
@NoArgsConstructor
@DynamicUpdate
@ToString
public class TodayDiscountedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "product_id", referencedColumnName = "product_id")
    private Product product;

    private int discountPrice;

    private double discountRate;

    private LocalDateTime createdAt;

    @Builder
    public TodayDiscountedProduct(Product product, int discountPrice, double discountRate, LocalDateTime createdAt ) {
        this.product = product;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.createdAt = createdAt;
    }
}
