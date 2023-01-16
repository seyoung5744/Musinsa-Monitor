package com.zerobase.musinsamonitor.crawler.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Category {
//    TOP(1), // 상의 001
//    OUTER(2), // 아우터 002
//    PANTS(3), //  바지 003
//    BAG(4), // 가방 004
//    SHOES(5), // 신발  005
//    CLOCK(6), // 시계 006
//    HAT(7), // 모자 007
//    SOCKS(8), // 양말/레그웨어 008
//    SUNGLASSES(9); // 선글라스/안경테 009

    TOP("001");

    private String category;
    private int num;

    Category(String category) {
        this.category = category;
    }

    public String getCategory(){
        return this.category;
    }

    /**
     * 숫자를 001 형식으로. ex) 10 -> 010
     *
     * @return "001"
     */
    public static List<String> getCategoryList() {
        return Arrays.stream(Category.values())
            .map(Category::getCategory)
            .collect(Collectors.toList());
    }

}
