package com.zerobase.musinsamonitor.model;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrawledResult {

    private List<Product> products;

    private List<Price> prices;

    public CrawledResult(){
        this.products = new ArrayList<>();
        this.prices = new ArrayList<>();
    }

}
