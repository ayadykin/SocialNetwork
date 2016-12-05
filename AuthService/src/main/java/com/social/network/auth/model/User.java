package com.social.network.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Yadykin Andrii Dec 5, 2016
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private long userId;

    private String name;
    
    public User(long userId, String name){
        this.userId = userId;
        this.name = name;
    }
}

