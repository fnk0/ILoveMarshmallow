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

    String brandName;
    String description;
    String asin;
    List<String> genders;
    String defaultProductType;
    String productName;
    String defaultImageUrl;
    List<ChildAsin> childAsins;

    public AsinProduct() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(List<String> genders) {
        this.genders = genders;
    }

    public String getDefaultProductType() {
        return defaultProductType;
    }

    public void setDefaultProductType(String defaultProductType) {
        this.defaultProductType = defaultProductType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDefaultImageUrl() {
        return defaultImageUrl;
    }

    public void setDefaultImageUrl(String defaultImageUrl) {
        this.defaultImageUrl = defaultImageUrl;
    }

    public List<ChildAsin> getChildAsins() {
        return childAsins;
    }

    public void setChildAsins(List<ChildAsin> childAsins) {
        this.childAsins = childAsins;
    }
}
