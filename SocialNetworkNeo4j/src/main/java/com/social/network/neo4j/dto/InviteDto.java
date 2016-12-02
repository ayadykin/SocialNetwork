package com.social.network.neo4j.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Yadykin Andrii Dec 1, 2016
 *
 */

@Getter
@Setter
@ToString
public class InviteDto implements Serializable{
    
    private long userId;
    private long friendId;
}

