package com.social.network.domain.dao.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.RecipientDao;
import com.social.network.domain.model.Recipient;
import com.social.network.domain.util.Constants;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
@Repository
public class RecipientDaoImpl extends GenericDaoHibernate<Recipient, Long> implements RecipientDao {
    private static final Logger logger = LoggerFactory.getLogger(RecipientDao.class);

    /**
     * Constructor set persistentClass for GenericDaoHibernate
     */
    public RecipientDaoImpl() {
        super(Recipient.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Recipient> findRecipientsByMessage(long messageId) {
        logger.debug(" findRecipientsByMessage");
        try {
            return getCurrentSession().getNamedQuery(Constants.FIND_RECIPIENT_BY_MESSAGE).setLong("messageId", messageId)
                    .list();
        } catch (Exception e) {
            logger.error("findByChatAndUser Exception : {}", e);
            return Collections.emptyList();
        }
    }
}
