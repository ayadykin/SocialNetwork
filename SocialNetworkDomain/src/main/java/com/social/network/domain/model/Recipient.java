package com.social.network.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQuery;

import com.social.network.domain.util.Constants;

/**
 * Created by Yadykin Andrii May 12, 2016
 *
 */

@Entity
@NamedQuery(name = Constants.FIND_RECIPIENT_BY_MESSAGE, query = "from Recipient r where r.messageId = :messageId")
public class Recipient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long resipientId;

    @Column(nullable = false)
    private boolean readed;

    @NotNull
    @OneToOne
    private User user;

    @Column(nullable = false)
    private long messageId;

    public Recipient() {

    }

    public Recipient(User user, long messageId) {
        this.user = user;
        this.messageId = messageId;
    }

    public void setResipientId(long resipientId) {
        this.resipientId = resipientId;
    }

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public long getResipientId() {
        return resipientId;
    }

    public User getUser() {
        return user;
    }

    public long getUserId() {
        return user.getUserId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Recipient [resipientId=" + resipientId + ", readed=" + readed + ", user=" + user + ", messageId="
                + messageId + "]";
    }

}
