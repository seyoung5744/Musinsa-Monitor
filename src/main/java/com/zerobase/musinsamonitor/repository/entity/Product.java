package com.zerobase.musinsamonitor.repository.entity;

import com.zerobase.musinsamonitor.crawler.dto.ProductDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity(name = "PRODUCT")
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Product implements Serializable {

    @Id
    @Column(name = "product_id")
    private int productId;

    private String productName;

    private String productUrl;

    private String productIcon;

    private String img;

    private String brand;

    private String brandUrl;

    // 마지막 순위 9000위
    @Column(columnDefinition = "SMALLINT")
    private int ranking;

    private String category;

    private int price;

    private float rating;

    private int ratingCount;

    private LocalDateTime updatedAt;


    @Builder
    public Product(ProductDto productDto) {
        this.productId = productDto.getProductId();
        this.productName = productDto.getProductName();
        this.productUrl = productDto.getProductUrl();
        this.productIcon = productDto.getProductIcon();
        this.img = productDto.getImg();
        this.brand = productDto.getBrand();
        this.brandUrl = productDto.getBrandUrl();
        this.ranking = productDto.getRanking();
        this.category = productDto.getCategory();
        this.price = productDto.getPrice();
        this.rating = productDto.getRating();
        this.ratingCount = productDto.getRatingCount();
        this.updatedAt = LocalDateTime.now();
    }
}
