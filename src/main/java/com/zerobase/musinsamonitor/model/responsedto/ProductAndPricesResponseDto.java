package com.zerobase.musinsamonitor.model.responsedto;

import com.zerobase.musinsamonitor.repository.entity.Price;
import com.zerobase.musinsamonitor.repository.entity.Product;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;


@Getter
public class ProductAndPricesResponseDto implements Serializable {

    private final String category;
    private final int productId;
    private final String productName;
    private final String productUrl;
    private final String brand;
    private final String brandUrl;
    private final int ranking;
    private final String img;
    private final LocalDateTime updatedAt;
    private final List<PriceResponseDto> prices;

    @Builder
    public ProductAndPricesResponseDto(Product product, List<Price> priceList) {
        this.category = product.getCategory();
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productUrl = product.getProductUrl();
        this.brand = product.getBrand();
        this.brandUrl = product.getBrandUrl();
        this.ranking = product.getRanking();
        this.img = product.getImg();
        this.updatedAt = product.getUpdatedAt();
        this.prices = priceList.stream().map(PriceResponseDto::new).collect(Collectors.toList());

    }
}
