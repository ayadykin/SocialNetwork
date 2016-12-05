package com.social.network.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Yadykin Andrii Dec 5, 2016
 *
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private long userId;
    private String name;

    private UserDto(String name) {
        this.name = name;
    }
}
