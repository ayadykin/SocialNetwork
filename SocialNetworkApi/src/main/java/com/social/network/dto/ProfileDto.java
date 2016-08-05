package com.social.network.dto;

import java.util.Locale;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

public class ProfileDto {

    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private Locale locale;
    private boolean translate;

    public ProfileDto() {

    }

    public ProfileDto(String firstName, String lastName, String city, String country, Locale locale, boolean translate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.locale = locale;
        this.translate = translate;
    }

    public Locale getLocale() {
        return locale;
    }

    public Locale[] getLocales() {
        return locale.getAvailableLocales();
    }

    public void setLocale(String locale) {
        this.locale = new Locale(locale);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean getTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "ProfileDto [firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", country="
                + country + ", locale=" + locale + ", translate=" + translate + "]";
    }

}
