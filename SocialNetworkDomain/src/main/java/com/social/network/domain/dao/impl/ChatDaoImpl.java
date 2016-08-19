package com.social.network.domain.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.social.network.domain.dao.ChatDao;
import com.social.network.domain.model.Chat;
import com.social.network.domain.model.Message;
import com.social.network.domain.model.User;

/**
 * Created by andrii.perylo on 5/13/2016.
 */
@Repository
public class ChatDaoImpl extends GenericDaoHibernate<Chat, Long> implements ChatDao {

    private static final Logger logger = LoggerFactory.getLogger(ChatDao.class);
    @Autowired
    private SessionFactory sessionFactory;

    public ChatDaoImpl() {
        super(Chat.class);
    }

    @Override
    public List<Message> getMessages(long chatId, User user, boolean readed, Date date) {

        logger.debug("getMessages : chatId = {}, userId = {},readed = {}, filter date = {} ", chatId, user.getUserId(), readed, date);
        Session session = sessionFactory.getCurrentSession();

        if (date != null) {
            session.enableFilter("messageLimit").setParameter("minDate", date);
        }

        Criteria criteria = session.createCriteria(Message.class, "message");
        criteria.createAlias("message.chat", "chat", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("message.recipient", "recipient");

        criteria.add(Restrictions.eq("chat.chatId", chatId));
        criteria.add(Restrictions.eq("recipient.user", user));
        criteria.add(Restrictions.eq("recipient.readed", readed));

        return criteria.list();
    }
}
