package com.social.network.domain.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Yadykin Andrii Sep 6, 2016
 *
 */

public class Role implements GrantedAuthority {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

}
