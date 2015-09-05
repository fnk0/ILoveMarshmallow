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
public class ValueUnitPair {

    String unit;
    float value;

    public ValueUnitPair() {
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
