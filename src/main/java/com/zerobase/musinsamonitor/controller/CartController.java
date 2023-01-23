package com.zerobase.musinsamonitor.controller;

import com.zerobase.musinsamonitor.model.requestdto.CartDeleteRequest;
import com.zerobase.musinsamonitor.model.responsedto.CartResponseDto;
import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.service.CartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')") // https://developer.okta.com/blog/2019/06/20/spring-preauthorize
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/save/{productId}")
    @ApiOperation("관심 상품 등록")
    public void saveProductToCart(@PathVariable("productId") int productId,
        @AuthenticationPrincipal MemberEntity member) {
        cartService.saveProductToCart(productId, member.getEmail());
    }

    @PostMapping("/cart/delete")
    @ApiOperation("관심 상품 삭제")
    public void deleteProductFromCart(@RequestBody CartDeleteRequest productIds,
        @AuthenticationPrincipal MemberEntity member) {
        log.info("삭제 번호: " + productIds.getProductIds());
        cartService.deleteProductFromCart(productIds.getProductIds(), member.getEmail());
    }

    @GetMapping("/cart")
    @ApiOperation("관심 상품 조회")
    public ResponseEntity<Page<CartResponseDto>> findAllCartList(@AuthenticationPrincipal MemberEntity member,
        Pageable pageable) {
        Page<CartResponseDto> result = cartService.findAllCartList(member.getEmail(), pageable);
        return ResponseEntity.ok(result);
    }


}
