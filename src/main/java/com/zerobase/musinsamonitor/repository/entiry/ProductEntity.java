package com.zerobase.musinsamonitor.repository.entiry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "product")
    List<PriceEntity> prices = new ArrayList<>();
}
