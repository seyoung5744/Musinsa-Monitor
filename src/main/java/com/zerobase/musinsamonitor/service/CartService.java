package com.zerobase.musinsamonitor.service;

import static com.zerobase.musinsamonitor.exception.ErrorCode.DUPLICATE_RESOURCE;
import static com.zerobase.musinsamonitor.exception.ErrorCode.NON_EXISTENT_PRODUCT;

import com.zerobase.musinsamonitor.dto.CartResponseDto;
import com.zerobase.musinsamonitor.dto.ProductResponseDto;
import com.zerobase.musinsamonitor.exception.CustomException;
import com.zerobase.musinsamonitor.exception.ErrorCode;
import com.zerobase.musinsamonitor.repository.CarRepository;
import com.zerobase.musinsamonitor.repository.ProductJpaRepository;
import com.zerobase.musinsamonitor.repository.entity.Cart;
import com.zerobase.musinsamonitor.repository.entity.Product;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (!exists) {
            throw new CustomException(NON_EXISTENT_PRODUCT);
        }

        Product product = productJpaRepository.findByProductId(productId)
            .orElseThrow(() -> new CustomException(NON_EXISTENT_PRODUCT));

        boolean cartExists = carRepository.existsByProductAndEmail(product, email);

        if(cartExists){
            throw new CustomException(DUPLICATE_RESOURCE);
        }

        carRepository.save(Cart.builder()
            .product(product)
            .email(email)
            .createdAt(LocalDateTime.now())
            .build());
    }

    public void deleteProductFromCart(String deleteNo, String email) {
        int[] deleteProductsId = Arrays.stream(deleteNo.trim().split(SPLIT_PREFIX)).mapToInt(Integer::parseInt)
            .toArray();

        for (int productId : deleteProductsId) {
            Product product = productJpaRepository.findByProductId(productId)
                .orElseThrow(() -> new CustomException(NON_EXISTENT_PRODUCT));

            carRepository.deleteByProductAndEmail(product, email);
        }

    }

    public Page<CartResponseDto> findAllCartList(String email, Pageable pageable) {
        return carRepository.findAllByEmail(email, pageable).map(e -> new CartResponseDto(e));
    }
}
