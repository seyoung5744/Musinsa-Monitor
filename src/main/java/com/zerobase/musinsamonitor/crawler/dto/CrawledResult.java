package com.zerobase.musinsamonitor.crawler.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrawledResult {

    private List<ProductDto> products;

    private List<PriceDto> prices;

}
