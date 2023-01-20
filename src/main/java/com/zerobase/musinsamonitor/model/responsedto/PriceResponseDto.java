package com.zerobase.musinsamonitor.model.responsedto;

import com.zerobase.musinsamonitor.repository.entity.Price;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PriceResponseDto implements Serializable {

    private final int price;
    private final int delPrice;
    private final int ranking;
    private final int coupon;
    private final LocalDateTime createdAt;

    @Builder
    public PriceResponseDto(Price price) {
        this.price = price.getPrice();
        this.delPrice = price.getDelPrice();
        this.ranking = price.getRanking();
        this.coupon = price.getCoupon();
        this.createdAt = price.getCreatedAt();
    }
}
