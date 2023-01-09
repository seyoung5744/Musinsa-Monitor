package com.zerobase.musinsamonitor.repository;

import static com.zerobase.musinsamonitor.repository.entiry.QProduct.product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.musinsamonitor.dto.ProductResponseDto;
import com.zerobase.musinsamonitor.repository.entiry.Product;
import com.zerobase.musinsamonitor.repository.entiry.QProduct;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;


    public Page<ProductResponseDto> findByBrand(String brandName, Pageable pageable) {
        List<Product> productList = jpaQueryFactory.selectFrom(product)
            .where(product.brand.eq(brandName))
            .orderBy(product.updatedAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        // List ->  Page
        return new PageImpl<>(productList.stream()
            .map(ProductResponseDto::new)
            .collect(Collectors.toList())
            , pageable
            , productList.size()
        );
    }
}
