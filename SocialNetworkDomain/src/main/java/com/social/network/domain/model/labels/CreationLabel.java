package com.social.network.domain.model.labels;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by Yadykin Andrii Jul 25, 2016
 *
 */

@Embeddable
public class CreationLabel implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public CreationLabel() {
        created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "CreationLabel [created=" + created + "]";
    }

}
