package com.gabilheri.ilovemarshmallow.data.endpoint_models;

import org.parceler.Parcel;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/15.
 */
@Parcel
public class LanguageTagged {

    String platformHeight;
    String lanaguageTag;
    String shoeWidth;
    String displaySize;

    public LanguageTagged() {
    }

    public String getPlatformHeight() {
        return platformHeight;
    }

    public void setPlatformHeight(String platformHeight) {
        this.platformHeight = platformHeight;
    }

    public String getLanaguageTag() {
        return lanaguageTag;
    }

    public void setLanaguageTag(String lanaguageTag) {
        this.lanaguageTag = lanaguageTag;
    }

    public String getShoeWidth() {
        return shoeWidth;
    }

    public void setShoeWidth(String shoeWidth) {
        this.shoeWidth = shoeWidth;
    }

    public String getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }
}
