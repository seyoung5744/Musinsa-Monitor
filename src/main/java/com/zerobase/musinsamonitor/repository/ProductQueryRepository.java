package com.zerobase.musinsamonitor.repository;

import static com.zerobase.musinsamonitor.repository.entity.QPrice.price1;
import static com.zerobase.musinsamonitor.repository.entity.QProduct.product;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.musinsamonitor.crawler.constants.Category;
import com.zerobase.musinsamonitor.model.responsedto.ProductResponseDto;
import com.zerobase.musinsamonitor.repository.entity.Price;
import com.zerobase.musinsamonitor.repository.entity.Product;
import java.time.LocalDateTime;
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
            .orderBy(product.ranking.asc(), product.updatedAt.desc())
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

    public Page<ProductResponseDto> findTodayProductByCategory(Category category, String period, Pageable pageable) {
        StringTemplate formattedDate = Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})"
            , product.updatedAt
            , ConstantImpl.create("%Y-%m-%d")
        );

        StringTemplate now = Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})"
            , LocalDateTime.now()
            , ConstantImpl.create("%Y-%m-%d")
        );

        List<Product> productList = jpaQueryFactory.selectFrom(product)
            .where(formattedDate.eq(now)
                , product.category.eq(category.getCategory())
            )
            .orderBy(product.ranking.asc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return new PageImpl<>(productList.stream()
            .map(ProductResponseDto::new)
            .collect(Collectors.toList())
            , pageable
            , productList.size()
        );
    }

    public List<Price> findPriceByProductId(int productId) {
        return jpaQueryFactory.selectFrom(price1)
            .where(price1.productId.eq(productId))
            .orderBy(price1.createdAt.asc())
            .fetch();
    }

    public Product findProductById(int productId) {
        return jpaQueryFactory.selectFrom(product)
            .where(product.productId.eq(productId))
            .orderBy(product.updatedAt.desc())
            .fetchOne();
    }
}
