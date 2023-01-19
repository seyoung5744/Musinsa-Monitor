package com.zerobase.musinsamonitor.service;

import static com.zerobase.musinsamonitor.exception.ErrorCode.DUPLICATE_RESOURCE;
import static com.zerobase.musinsamonitor.exception.ErrorCode.NON_EXISTENT_PRODUCT;

import com.zerobase.musinsamonitor.exception.CustomException;
import com.zerobase.musinsamonitor.exception.ErrorCode;
import com.zerobase.musinsamonitor.repository.CarRepository;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.entity.Cart;
import com.zerobase.musinsamonitor.repository.entity.Product;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CarRepository carRepository;
    private final ProductJpaRepository productJpaRepository;

    private static final String SPLIT_PREFIX = "\\.";

    public void saveProductToCart(int productId, String email) {

        boolean exists = productJpaRepository.existsByProductId(productId);

        if (exists) {
            throw new CustomException(DUPLICATE_RESOURCE);
        }

        Product product = productJpaRepository.findByProductId(productId)
            .orElseThrow(() -> new CustomException(NON_EXISTENT_PRODUCT));

        carRepository.save(Cart.builder()
            .product(product)
            .email(email)
            .createdAt(LocalDateTime.now())
            .build());
    }

    public void deleteProductFromCart(String deleteNo, String email) {
        int[] deleteProductsId = Arrays.stream(deleteNo.trim().split(SPLIT_PREFIX)).mapToInt(Integer::parseInt).toArray();



    }
}
