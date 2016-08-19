package com.social.network.domain.dao;

import java.util.List;

import com.social.network.domain.model.Recipient;

/**
 * Created by andrii.perylo on 5/13/2016.
 */

public interface RecipientDao extends GenericDao<Recipient, Long> {
    List<Recipient> findRecipientsByMessage(long messageId);
}
