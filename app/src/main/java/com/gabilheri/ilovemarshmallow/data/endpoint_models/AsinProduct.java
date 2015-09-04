package com.gabilheri.ilovemarshmallow.data.endpoint_models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
@Parcel
public class AsinProduct {

    private String brandName;
    private String description;
    private String asin;
    private List<String> genders;
    private String defaultProductType;
    private String productName;
    private String defaultImageUrl;

    public AsinProduct() {
    }

    public String getBrandName() {
        return brandName;
    }

    public AsinProduct setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AsinProduct setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAsin() {
        return asin;
    }

    public AsinProduct setAsin(String asin) {
        this.asin = asin;
        return this;
    }

    public List<String> getGenders() {
        return genders;
    }

    public AsinProduct setGenders(List<String> genders) {
        this.genders = genders;
        return this;
    }

    public String getDefaultProductType() {
        return defaultProductType;
    }

    public AsinProduct setDefaultProductType(String defaultProductType) {
        this.defaultProductType = defaultProductType;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public AsinProduct setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }

    public AsinProduct setDefaultImageUrl(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
        return this;
    }
}
