package com.social.network.domain.model.labels;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Created by Yadykin Andrii Jul 25, 2016
 *
 */

@Embeddable
public class HiddenLabel implements Serializable {
    private boolean hidden;

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "HiddenLabel [hidden=" + hidden + "]";
    }

}
