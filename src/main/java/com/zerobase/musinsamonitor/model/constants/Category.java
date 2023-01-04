package com.zerobase.musinsamonitor.model.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Category {
    TOP(1),
    OUTER(2),
    PANTS(3),
    BAG(4),
    SHOES(5),
    CLOCK(6),
    HAT(7),
    SOCKS(8),
    SUNGLASSES(9);

    private int number;

    Category(int n) {
        this.number = n;
    }

    /**
     * 숫자를 001 형식으로. ex) 10 -> 010
     * @return "001"
     */
    public static List<String> numToString() {
        List<String> categories = new ArrayList<>();
        for (Category c : Category.values()) {
            categories.add(String.format("%03d", c.number));
        }
        return categories;
    }

}
