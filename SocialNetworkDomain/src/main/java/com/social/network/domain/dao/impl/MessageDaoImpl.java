package com.social.network.domain.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.MessageDao;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
@Repository
public class MessageDaoImpl extends GenericDaoHibernate<Message, Long> implements MessageDao{

    private final static Logger logger = LoggerFactory.getLogger(MessageDao.class);
    
    public MessageDaoImpl(){
        super(Message.class);
    }
    
    @Override
    public List<Message> getMessages(long chatId, User user, boolean readed, Date date) {

        logger.debug("getMessages : chatId = {}, userId = {},readed = {}, filter date = {} ", chatId, user.getUserId(), readed, date);

        if (date != null) {
            getCurrentSession().enableFilter("messageLimit").setParameter("minDate", date);
        }

        Criteria criteria = getCurrentSession().createCriteria(Message.class, "message");
        criteria.createAlias("message.chat", "chat", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("message.recipient", "recipient");

        criteria.add(Restrictions.eq("chat.chatId", chatId));
        criteria.add(Restrictions.eq("recipient.user", user));
        criteria.add(Restrictions.eq("recipient.readed", readed));

        return criteria.list();
    }
}
