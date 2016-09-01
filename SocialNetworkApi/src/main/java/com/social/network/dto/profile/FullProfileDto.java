package com.social.network.dto.profile;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

public class FullProfileDto extends PublicProfileDto {

    private String locale;
    private boolean translate;

    public FullProfileDto() {

    }

    public FullProfileDto(String firstName, String lastName, String street, String city, String country, String locale, boolean translate) {
        super(firstName, lastName, street, city, country);
        this.locale = locale;
        this.translate = translate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean getTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "FullProfileDto [locale=" + locale + ", translate=" + translate + "]";
    }

}
