package com.zerobase.musinsamonitor.repository;

import static com.zerobase.musinsamonitor.repository.entity.QProduct.product;
import static com.zerobase.musinsamonitor.repository.entity.QTodayDiscountedProduct.todayDiscountedProduct;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zerobase.musinsamonitor.dto.ProductAndDiscountResponseDto;
import com.zerobase.musinsamonitor.repository.entity.TodayDiscountedProduct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodayDiscountedProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * select count(*)
     * from discount
     * 	inner join product
     *     on discount.product_id = product.product_id
     *     where discount.created_at >= curdate()
     * order by product.ranking asc;
     */
    public Page<ProductAndDiscountResponseDto> findTodayDiscountedProducts(Pageable pageable) {
        StringTemplate formattedDate = Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})"
            , todayDiscountedProduct.createdAt
            , ConstantImpl.create("%Y-%m-%d")
        );

        StringTemplate now = Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})"
            , LocalDateTime.now()
            , ConstantImpl.create("%Y-%m-%d")
        );

        List<TodayDiscountedProduct> discountedProductList = jpaQueryFactory.selectFrom(todayDiscountedProduct)
            .innerJoin(todayDiscountedProduct.product)
            .where(
                formattedDate.eq(now)
            )
            .orderBy(todayDiscountedProduct.product.ranking.asc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        return new PageImpl<>(discountedProductList.stream()
            .map(ProductAndDiscountResponseDto::new)
            .collect(Collectors.toList())
            , pageable
            , discountedProductList.size()
        );
    }
}
