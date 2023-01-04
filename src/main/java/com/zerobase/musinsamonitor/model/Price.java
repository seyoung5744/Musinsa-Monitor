package com.zerobase.musinsamonitor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Price {

    private int productId;

    private int ranking;

    private int price;

    private int delPrice;

    private int coupon;
}
