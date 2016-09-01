package com.social.network.dto.chat;

import java.util.Date;

/**
 * Created by Yadykin Andrii Aug 31, 2016
 *
 */

public class GetChatMessagesDto extends ChatIdDto {

    private Date dateFilter;

    public GetChatMessagesDto() {

    }

    public GetChatMessagesDto(long chatId, Date dateFilter) {
        super(chatId);
        this.dateFilter = dateFilter;
    }

    public Date getDateFilter() {
        return dateFilter;
    }

    public void setDateFilter(Date dateFilter) {
        this.dateFilter = dateFilter;
    }

}
