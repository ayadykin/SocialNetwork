package com.social.network.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.social.network.model.enums.SystemMessageStatus;

/**
 * Created by Yadykin Andrii Jul 19, 2016
 *
 */
@Entity
@Table(name = "system_message")
@PrimaryKeyJoinColumn(name = "messageId")
public class SystemMessage extends Message implements Serializable {

    @Enumerated(EnumType.STRING)
    private SystemMessageStatus systemMessageStatus;

    public SystemMessage() {

    }

    public SystemMessage(String text, User publisher, Chat chat, SystemMessageStatus systemMessageStatus) {
        super(text, publisher, chat);
        this.systemMessageStatus = systemMessageStatus;
    }

    public SystemMessageStatus getSystemMessageStatus() {
        return systemMessageStatus;
    }

    public SystemMessage setStatusInvite() {
        this.systemMessageStatus = SystemMessageStatus.INVITE;
        return this;
    }

    public SystemMessage setStatusSystem() {
        this.systemMessageStatus = SystemMessageStatus.SYSTEM;
        return this;
    }

}
