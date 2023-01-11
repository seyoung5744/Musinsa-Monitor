package com.zerobase.musinsamonitor.crawler;


import com.zerobase.musinsamonitor.crawler.dto.CrawledResult;
import com.zerobase.musinsamonitor.crawler.dto.PriceDto;
import com.zerobase.musinsamonitor.crawler.dto.ProductDto;
import com.zerobase.musinsamonitor.crawler.constants.Category;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;


@Component
public class MusinsaCrawler implements Crawler {

    private static final String URL = "https://www.musinsa.com/ranking/best?period=now&mainCategory=%s&kids=false&newProduct=false&exclusive=false&discount=false&soldOut=false&page=%d&viewType=small";
    private static final int START_PAGE = 1;
    private static final int END_PAGE = 1;

    @Override
    public CrawledResult crawling() {
        List<ProductDto> productList = new ArrayList<>();
        List<PriceDto> priceList = new ArrayList<>();

        for (String category : Category.getCategoryList()) {
            for (int page = START_PAGE; page <= END_PAGE; page++) {

                try {
                    String url = String.format(URL, category, page);

                    Document document = Jsoup.connect(url)
                        .userAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                        .get();

                    Elements goodsRankList = document.select("#goodsRankList");
                    Elements li = goodsRankList.select(".li_box");

                    for (Element element : li) {
                        int productId = Integer.parseInt(
                            element.select("li").attr("data-goods-no"));
                        String productName = element.select("p.list_info > a").text();
                        String productUrl = element.select("p[class=list_info] > a").attr("href");
                        String productIcon = element.select("div[class=box-icon-right]").text();
                        String img = element.getElementsByTag("img").attr("data-original");
                        String brand = element.select("p.item_title > a").text();
                        String brandUrl = element.select("p.item_title > a").attr("href");
                        int ranking = Integer.parseInt(
                            element.select(".txt_num_rank").text().replace("위", ""));

                        String prices = element.select("p[class=price]").text();
                        int price = 0;
                        int delPrice = 0;

                        if (prices.contains(" ")) {
                            price = Integer.parseInt(
                                prices.split(" ")[1].split("원")[0].replace(",", ""));
                            delPrice = Integer.parseInt(
                                prices.split(" ")[0].split("원")[0].replace(",", ""));
                        } else {
                            price = Integer.parseInt(prices.split("원")[0].replace(",", ""));
                        }

                        Elements pointEle = element.select("p[class=point]");
                        float rating = 0;
                        int ratingCount = 0;
                        if (!pointEle.isEmpty()) {

                            rating = Float.parseFloat(
                                pointEle.select("span[class=bar]").attr("style")
                                    .split(" ")[1].replace("%", ""));
                            ratingCount = Integer.parseInt(
                                pointEle.select("span[class=count]").text().replace(",", ""));
                        }

                        Elements couponEle = element.select("span[class=txt_discount_price]");
                        int coupon = 0;
                        if (!couponEle.isEmpty()) {
                            coupon = Integer.parseInt(
                                couponEle.text().split("원")[0].replace(",", "")
                                    .replace("-", "")); // 쿠폰
                        }

                        ProductDto product = ProductDto.builder()
                            .productId(productId)
                            .productName(productName)
                            .productUrl(productUrl)
                            .productIcon(productIcon)
                            .img(img)
                            .brand(brand)
                            .brandUrl(brandUrl)
                            .ranking(ranking)
                            .category(category)
                            .price(price)
                            .rating(rating)
                            .ratingCount(ratingCount)
                            .build();
                        productList.add(product);

                        priceList.add(PriceDto.builder()
                            .productId(productId)
                            .ranking(ranking)
                            .price(price)
                            .delPrice(delPrice)
                            .coupon(coupon)
                            .build());


                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return new CrawledResult(productList, priceList);
    }
}
