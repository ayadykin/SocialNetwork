package com.social.network.neo4j.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Yadykin Andrii Dec 1, 2016
 *
 */

@Setter
@Getter
public class GroupDto {
    private long ownerId;
    private String groupName;
    private long[] userIds;
}

