package com.social.network.domain.model;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Yadykin Andrii May 16, 2016
 *
 */

@Entity
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profileId;

    @Column
    private String phone;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private Locale locale;

    @Column
    private boolean translate;

    public Profile() {

    }

    public Profile(Locale locale) {
        this.locale = locale;
    }

    public Profile(String phone, String street, String city, String country, Locale locale) {
        this(locale);
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.country = country;

    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "Profile [profileId=" + profileId + ", phone=" + phone + ", street=" + street + ", city=" + city + ", country=" + country
                + ", locale=" + locale + ", translate=" + translate + "]";
    }

}
