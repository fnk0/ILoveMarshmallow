package com.gabilheri.ilovemarshmallow.data.endpoint_models;

import android.content.ContentValues;
import android.database.Cursor;

import com.gabilheri.ilovemarshmallow.data.DataContract;

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
    float productRating;
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

    public float getProductRating() {
        return productRating;
    }

    public SearchResultItem setProductRating(float productRating) {
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

    public static ContentValues toContentValues(SearchResultItem item) {
        ContentValues values = new ContentValues();
        values.put(DataContract.SearchResultEntry.BRAND_NAME, item.getBrandName());
        values.put(DataContract.SearchResultEntry.ORIGINAL_PRICE, item.getOriginalPrice());
        values.put(DataContract.SearchResultEntry.PRICE, item.getPrice());
        values.put(DataContract.SearchResultEntry.IMAGE_URL, item.getImageUrl());
        values.put(DataContract.SearchResultEntry.ASIN, item.getAsin());
        values.put(DataContract.SearchResultEntry.PRODUCT_URL, item.getProductUrl());
        values.put(DataContract.SearchResultEntry.PRODUCT_RATING, item.getProductRating());
        values.put(DataContract.SearchResultEntry.PRODUCT_NAME, item.getProductName());
        return values;
    }

    public static SearchResultItem fromCursor(Cursor cursor, boolean close) {
        SearchResultItem item = new SearchResultItem();
        if (cursor.getCount() == 0) {
            return null;
        }

        if (cursor.getPosition() == -1) {
            cursor.moveToNext();
        }

        item.setBrandName(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.BRAND_NAME)))
                .setOriginalPrice(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.ORIGINAL_PRICE)))
                .setPrice(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.PRICE)))
                .setImageUrl(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.IMAGE_URL)))
                .setAsin(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.ASIN)))
                .setProductUrl(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.PRODUCT_URL)))
                .setProductRating(cursor.getFloat(cursor.getColumnIndex(DataContract.SearchResultEntry.PRODUCT_RATING)))
                .setProductName(cursor.getString(cursor.getColumnIndex(DataContract.SearchResultEntry.PRODUCT_NAME)));

        if (close) {
            cursor.close();
        }
        return item;
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
