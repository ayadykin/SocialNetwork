package com.social.network.dao;

import java.util.List;

import com.social.network.model.Recipient;

/**
 * Created by andrii.perylo on 5/13/2016.
 */

public interface RecipientDao extends GenericDao<Recipient, Long> {
    List<Recipient> findRecipientsByMessage(long messageId);
}
