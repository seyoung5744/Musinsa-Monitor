package com.zerobase.musinsamonitor.repository.entity;

import com.zerobase.musinsamonitor.crawler.dto.PriceDto;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "PRICE")
@Getter
@NoArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int productId;

    @Column(columnDefinition = "SMALLINT")
    private int ranking;

    private int price;

    private int delPrice;

    private int coupon;

    private LocalDateTime createdAt;

    @Builder
    public Price(PriceDto priceDto){
//        this.product = new Product(priceDto.getProduct());
        this.productId = priceDto.getProductId();
        this.ranking = priceDto.getRanking();
        this.price = priceDto.getPrice();
        this.delPrice = priceDto.getDelPrice();
        this.coupon = priceDto.getCoupon();
        this.createdAt = LocalDateTime.now();
    }
}
