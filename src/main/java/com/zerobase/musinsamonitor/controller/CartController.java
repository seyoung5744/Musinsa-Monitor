package com.zerobase.musinsamonitor.controller;

import com.zerobase.musinsamonitor.repository.entity.MemberEntity;
import com.zerobase.musinsamonitor.security.jwt.TokenProvider;
import com.zerobase.musinsamonitor.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')") // https://developer.okta.com/blog/2019/06/20/spring-preauthorize
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/save/{productId}")
    public void saveProductToCart(@PathVariable("productId") int productId, @AuthenticationPrincipal MemberEntity member){
        cartService.saveProductToCart(productId, member.getEmail());
    }

    @PostMapping("/cart/delete")
    public void deleteProductFromCart(@RequestParam("delete_no") String deleteNo, @AuthenticationPrincipal MemberEntity member){
        log.info("삭제 : " + deleteNo);
        cartService.deleteProductFromCart(deleteNo, member.getEmail());
    }


}