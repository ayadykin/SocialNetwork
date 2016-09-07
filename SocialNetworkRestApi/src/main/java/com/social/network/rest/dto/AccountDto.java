package com.social.network.rest.dto;

import java.io.Serializable;

/**
 * Created by Yadykin Andrii Sep 5, 2016
 *
 */

public class AccountDto implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String locale;

    public AccountDto() {

    }

    public AccountDto(String email, String firstName, String lastName, String locale) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}
