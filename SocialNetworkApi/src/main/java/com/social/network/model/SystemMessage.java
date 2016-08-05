package com.social.network.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;

import com.social.network.model.enums.SystemMessageStatus;
import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii Jul 19, 2016
 *
 */
@Entity
@Table(name = "system_message")
@NamedQuery(name = Constants.FIND_SYSTEM_MESSAGE_BY_CHAT, query = "from SystemMessage sm where sm.message.chat = :chat")
public class SystemMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private SystemMessageStatus systemMessageStatus;

    @NotNull
    @OneToOne
    private Message message;

    public SystemMessage() {

    }

    public SystemMessage(Message message, SystemMessageStatus systemMessageStatus) {
        this.message = message;
        this.systemMessageStatus = systemMessageStatus;
    }

    public long getId() {
        return id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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
