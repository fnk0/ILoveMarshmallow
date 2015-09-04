package com.gabilheri.ilovemarshmallow.data.endpoint_models;

import org.parceler.Parcel;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
@Parcel
public class SearchResultItem {

    String brandName;
    String originalPrice;
    String price;
    String imageUrl;
    String asin;
    String productUrl;
    int productRating;
    String map;
    String productName;

    public SearchResultItem() {
    }

    public String getBrandName() {
        return brandName;
    }

    public SearchResultItem setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public SearchResultItem setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public SearchResultItem setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SearchResultItem setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAsin() {
        return asin;
    }

    public SearchResultItem setAsin(String asin) {
        this.asin = asin;
        return this;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public SearchResultItem setProductUrl(String productUrl) {
        this.productUrl = productUrl;
        return this;
    }

    public int getProductRating() {
        return productRating;
    }

    public SearchResultItem setProductRating(int productRating) {
        this.productRating = productRating;
        return this;
    }

    public String getMap() {
        return map;
    }

    public SearchResultItem setMap(String map) {
        this.map = map;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public SearchResultItem setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    @Override
    public String toString() {
        return "SearchResultItem{" +
                "brandName='" + brandName + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", price='" + price + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", asin='" + asin + '\'' +
                ", productUrl='" + productUrl + '\'' +
                ", productRating=" + productRating +
                ", map='" + map + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
