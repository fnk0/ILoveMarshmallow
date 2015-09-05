package com.gabilheri.ilovemarshmallow.data.endpoint_models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/4/15.
 */
@Parcel
public class AsinSizing {

    ValueUnitPair heelHeight;
    ValueUnitPair weight;
    List<LanguageTagged> languageTagged;

    public AsinSizing() {
    }

    public ValueUnitPair getHeelHeight() {
        return heelHeight;
    }

    public void setHeelHeight(ValueUnitPair heelHeight) {
        this.heelHeight = heelHeight;
    }

    public ValueUnitPair getWeight() {
        return weight;
    }

    public void setWeight(ValueUnitPair weight) {
        this.weight = weight;
    }

    public List<LanguageTagged> getLanguageTagged() {
        return languageTagged;
    }

    public void setLanguageTagged(List<LanguageTagged> languageTagged) {
        this.languageTagged = languageTagged;
    }
}
