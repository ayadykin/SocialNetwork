package com.social.network.dto.profile;

/**
 * Created by Yadykin Andrii May 17, 2016
 *
 */

public class PublicProfileDto {

    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String country;

    public PublicProfileDto() {

    }

    public PublicProfileDto(String firstName, String lastName, String street, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.country = country;

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

    @Override
    public String toString() {
        return "PublicProfileDto [firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", country=" + country + "]";
    }

}
