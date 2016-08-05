package com.social.network.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.dao.SystemMessageDao;
import com.social.network.model.Chat;
import com.social.network.model.SystemMessage;
import com.social.network.utils.Constants;

/**
 * Created by Yadykin Andrii Jul 26, 2016
 *
 */
@Repository
public class SystemMessageDaoImpl extends GenericDaoHibernate<SystemMessage, Long> implements SystemMessageDao {

    private static final Logger logger = LoggerFactory.getLogger(SystemMessageDao.class);

    public SystemMessageDaoImpl() {
        super(SystemMessage.class);
    }

    @Override
    public SystemMessage findSystemMessageByChat(Chat chat) {
        try {
            return (SystemMessage) getCurrentSession().getNamedQuery(Constants.FIND_SYSTEM_MESSAGE_BY_CHAT)
                    .setEntity("chat", chat).uniqueResult();
        } catch (NonUniqueResultException e) {
            logger.debug("findGroupByChat NonUniqueResultException : {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.debug("findGroupByChat Exception : {}", e.getMessage());
            return null;
        }
    }

}
