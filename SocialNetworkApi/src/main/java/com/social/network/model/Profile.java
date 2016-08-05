package com.social.network.model;

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
    private String firstName;

    @Column
    private String lastName;

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

    public Profile(String firstName, String lastName, String city, String country, Locale locale) {
        this(locale);
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
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
        return "Profile [profileId=" + profileId + ", firstName=" + firstName + ", lastName=" + lastName + ", city="
                + city + ", country=" + country + "]";
    }

}
